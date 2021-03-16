package com.uc.servingwebcontent.webController;

public class Request {
    private String from;
    private String to;

    public Request(String form, String to) {
        this.from = form;
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
