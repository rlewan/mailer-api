package com.github.rlewan.mailer.model;

import org.springframework.http.HttpStatus;

public class SendEmailResponse {

    public static final SendEmailResponse ACCEPTED =
        new SendEmailResponse(HttpStatus.ACCEPTED.value(), "Your request has been dispatched");

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
