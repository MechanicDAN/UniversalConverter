package com.uc.servingwebcontent.webController;

public class Response {

    private final String status;
    private final Integer code;
    private final String body;

    public Response(String status, Integer code, String body) {
        this.status = status;
        this.code = code;
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public Integer getCode() {
        return code;
    }
}