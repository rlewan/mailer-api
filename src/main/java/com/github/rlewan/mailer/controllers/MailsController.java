package com.github.rlewan.mailer.controllers;

import com.github.rlewan.mailer.emailsenders.EmailSender;
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

    private final EmailSender emailSender;

    @Autowired
    public MailsController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void sendEmail(@RequestBody SendEmailRequest request) {
        System.out.println(request.getRecipient());
        System.out.println(request.getSubject());
        System.out.println(request.getContent());
    }

    @GetMapping
    public String sayHello() {
        emailSender.sendEmail(
            "rafal_lewandowski@outlook.com",
            "rlewano@gmail.com",
            "Testing",
            "Some text"
        );
        return "Hello, World!";
    }

}
