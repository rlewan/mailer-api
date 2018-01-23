package com.github.rlewan.mailer.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class SendEmailRequest {

    @Email
    @NotBlank
    private final String recipient;

    private final String subject;

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
