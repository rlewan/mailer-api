package com.github.rlewan.mailer.model;

public class SendEmailResponse {

    public static final SendEmailResponse SUCCESS = new SendEmailResponse(200, "Email sent successfully");

    private final Integer status;
    private final String message;

    public SendEmailResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
