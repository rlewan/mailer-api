package com.github.rlewan.mailer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

@ApiModel(description = "JSON body of the send email response")
public class SendEmailResponse {

    public static final SendEmailResponse ACCEPTED =
        new SendEmailResponse(HttpStatus.ACCEPTED.value(), "Your request has been dispatched");

    @ApiModelProperty(
        example = "202",
        position = 1
    )
    private final Integer status;

    @ApiModelProperty(
        example = "Your request has been dispatched",
        position = 2
    )
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
