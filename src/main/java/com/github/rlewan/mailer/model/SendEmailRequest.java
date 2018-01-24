package com.github.rlewan.mailer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@ApiModel(description = "JSON body of the send email request")
public class SendEmailRequest {

    @Email
    @NotBlank
    @ApiModelProperty(
        required = true,
        allowableValues = "valid email address",
        example = "user@emailserver.com",
        position = 1
    )
    private final String recipient;

    @NotBlank
    @ApiModelProperty(
        required = true,
        example = "Greetings from the Caribbean",
        position = 2
    )
    private final String subject;

    @NotBlank
    @ApiModelProperty(
        required = true,
        example = "We're having a great time here!",
        position = 3
    )
    private final String content;

    public SendEmailRequest(String recipient, String subject, String content) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

}
