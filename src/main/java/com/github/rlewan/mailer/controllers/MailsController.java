package com.github.rlewan.mailer.controllers;

import com.github.rlewan.mailer.emailsenders.EmailSender;
import com.github.rlewan.mailer.model.SendEmailRequest;
import com.github.rlewan.mailer.model.SendEmailResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/mails")
public class MailsController {

    private final EmailSender emailSender;

    @Autowired
    public MailsController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SendEmailResponse sendEmail(@RequestBody SendEmailRequest request) {
        emailSender.sendEmail(
            "rafal_lewandowski@outlook.com",
            request.getRecipient(),
            request.getSubject(),
            request.getContent()
        );
        return SendEmailResponse.SUCCESS;
    }

}
