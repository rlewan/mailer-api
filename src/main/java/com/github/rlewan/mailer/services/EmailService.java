package com.github.rlewan.mailer.services;

import com.github.rlewan.mailer.model.SendEmailRequest;
import com.github.rlewan.mailer.services.emailsenders.EmailSender;
import com.github.rlewan.mailer.exceptions.ServiceUnavailableException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final String sender;
    private final EmailSender primaryEmailSender;
    private final EmailSender secondaryEmailSender;

    @Autowired
    public EmailService(
        @Value("${mailer.sender}") String sender,
        @Qualifier("primaryEmailSender") EmailSender primaryEmailSender,
        @Qualifier("secondaryEmailSender") EmailSender secondaryEmailSender
    ) {
        this.sender = sender;
        this.primaryEmailSender = primaryEmailSender;
        this.secondaryEmailSender = secondaryEmailSender;
    }

    @HystrixCommand(fallbackMethod = "sendEmailUsingSecondarySender")
    public void sendEmail(SendEmailRequest request) {
        primaryEmailSender.sendEmail(sender, request.getRecipient(), request.getSubject(), request.getContent());
    }

    @SuppressWarnings("unused")
    @HystrixCommand(fallbackMethod = "reportServiceUnavailable")
    public void sendEmailUsingSecondarySender(SendEmailRequest request) {
        secondaryEmailSender.sendEmail(sender, request.getRecipient(), request.getSubject(), request.getContent());
    }

    @SuppressWarnings("unused")
    public void reportServiceUnavailable(SendEmailRequest request) {
        throw new ServiceUnavailableException();
    }

}
