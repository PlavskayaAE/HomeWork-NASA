package ru.netology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;


public class Main {
    public static final String URL = "https://api.nasa.gov/planetary/apod?api_key=xeveBjcbIGZ5t4DhqkV8qqI5cadHPkTfOMx9Ek9P";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(50000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(URL);
        CloseableHttpResponse response = httpClient.execute(request);
        Image imageNASA = mapper.readValue(response.getEntity().getContent(), Image.class);
        System.out.println(imageNASA.toString());

        request = new HttpGet(imageNASA.getUrl());
        String[] separatedUrl = imageNASA.getUrl().split("/");
        String fileName = separatedUrl[separatedUrl.length - 1];
        CloseableHttpResponse img = httpClient.execute(request);

        FileOutputStream fos = new FileOutputStream(fileName);
        img.getEntity().writeTo(fos);
    }
}