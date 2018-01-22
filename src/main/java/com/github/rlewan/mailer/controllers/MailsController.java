package com.github.rlewan.mailer.controllers;

import com.github.rlewan.mailer.emailsenders.SendgridEmailSender;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/mails")
public class MailsController {

    private SendgridEmailSender sendgridEmailSender;

    @Autowired
    public MailsController(SendgridEmailSender sendgridEmailSender) {
        this.sendgridEmailSender = sendgridEmailSender;
    }

    @GetMapping
    public String sayHello() {
        sendgridEmailSender.sendEmail(
            "rafal_lewandowski@outlook.com",
            "rlewano@gmail.com",
            "Testing",
            "Some text"
        );
        return "Hello, World!";
    }

}
