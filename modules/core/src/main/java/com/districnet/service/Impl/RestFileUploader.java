package com.districtnet.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
@Component
public class RestFileUploader {

    private RestFileUploader() {
        // закрытый конструктор — класс утилитный
    }

    public static void uploadFiles(String serverUrl, File file1, File file2) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost uploadRequest = new HttpPost(serverUrl);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("file1", new FileBody(file1));
            builder.addPart("file2", new FileBody(file2));

            uploadRequest.setEntity(builder.build());

            try (CloseableHttpResponse response = httpClient.execute(uploadRequest)) {
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("Ответ сервера: " + statusCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
