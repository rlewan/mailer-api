package com.github.rlewan.mailer.controllers;

import com.github.rlewan.mailer.emailsenders.MailjetEmailSender;
import com.github.rlewan.mailer.emailsenders.SendgridEmailSender;
import com.github.rlewan.mailer.model.SendEmailRequest;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/mails")
public class MailsController {

    private SendgridEmailSender sendgridEmailSender;
    private MailjetEmailSender mailjetEmailSender;

    @Autowired
    public MailsController(
        SendgridEmailSender sendgridEmailSender,
        MailjetEmailSender mailjetEmailSender
    ) {
        this.sendgridEmailSender = sendgridEmailSender;
        this.mailjetEmailSender = mailjetEmailSender;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void sendEmail(@RequestBody SendEmailRequest request) {
        System.out.println(request.getRecipient());
        System.out.println(request.getSubject());
        System.out.println(request.getContent());
    }

    @GetMapping
    public String sayHello() {
        sendgridEmailSender.sendEmail(
            "rafal_lewandowski@outlook.com",
            "rlewano@gmail.com",
            "Testing",
            "Some text"
        );
        mailjetEmailSender.sendEmail(
            "rafal_lewandowski@outlook.com",
            "rlewano@gmail.com",
            "Testing",
            "Some text"
        );
        return "Hello, World!";
    }

}
