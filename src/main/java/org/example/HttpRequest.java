package org.example;


import lombok.Data;

import java.util.Map;

@Data
public class HttpRequest {
    private String ip;
    private String url;
    private Map<String, String> headers;

    public static HttpRequest from(String line) {
        HttpRequest httpRequest = new HttpRequest();
        String[] parts = line.split(";");
        httpRequest.setIp(parts[0]);
        httpRequest.setUrl(parts[1]);
        return httpRequest;
    }
}
