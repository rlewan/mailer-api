package com.github.rlewan.mailer.services;

import com.github.rlewan.mailer.services.emailsenders.EmailSender;
import com.github.rlewan.mailer.exceptions.ServiceUnavailableException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final EmailSender primaryEmailSender;
    private final EmailSender secondaryEmailSender;

    @Autowired
    public EmailService(
        @Qualifier("primaryEmailSender") EmailSender primaryEmailSender,
        @Qualifier("secondaryEmailSender") EmailSender secondaryEmailSender
    ) {
        this.primaryEmailSender = primaryEmailSender;
        this.secondaryEmailSender = secondaryEmailSender;
    }

    @HystrixCommand(fallbackMethod = "sendEmailUsingSecondarySender")
    public void sendEmail(String sender, String recipient, String subject, String text) {
        primaryEmailSender.sendEmail(sender, recipient, subject, text);
    }

    @SuppressWarnings("unused")
    @HystrixCommand(fallbackMethod = "reportServiceUnavailable")
    public void sendEmailUsingSecondarySender(String sender, String recipient, String subject, String text) {
        secondaryEmailSender.sendEmail(sender, recipient, subject, text);
    }

    @SuppressWarnings("unused")
    public void reportServiceUnavailable(String sender, String recipient, String subject, String text) {
        throw new ServiceUnavailableException();
    }

}