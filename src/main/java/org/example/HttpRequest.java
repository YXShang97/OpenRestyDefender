package org.example;


import lombok.Data;

import java.util.Map;

@Data
public class HttpRequest {
    private String ip;
    private String url;
    private Map<String, String> headers;
}
