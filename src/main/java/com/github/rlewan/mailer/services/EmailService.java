package com.github.rlewan.mailer.services;

import com.github.rlewan.mailer.model.SendEmailRequest;
import com.github.rlewan.mailer.services.emailserviceproviders.EmailServiceProvider;
import com.github.rlewan.mailer.exceptions.ServiceUnavailableException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final String sender;
    private final EmailServiceProvider primaryEmailServiceProvider;
    private final EmailServiceProvider secondaryEmailServiceProvider;

    @Autowired
    public EmailService(
        @Value("${mailer.sender}") String sender,
        @Qualifier("primaryEmailServiceProvider") EmailServiceProvider primaryEmailServiceProvider,
        @Qualifier("secondaryEmailServiceProvider") EmailServiceProvider secondaryEmailServiceProvider
    ) {
        this.sender = sender;
        this.primaryEmailServiceProvider = primaryEmailServiceProvider;
        this.secondaryEmailServiceProvider = secondaryEmailServiceProvider;
    }

    @HystrixCommand(fallbackMethod = "sendEmailUsingSecondarySender")
    public void sendEmail(SendEmailRequest request) {
        primaryEmailServiceProvider.sendEmail(sender, request.getRecipient(), request.getSubject(), request.getContent());
    }

    @SuppressWarnings("unused")
    @HystrixCommand(fallbackMethod = "reportServiceUnavailable")
    public void sendEmailUsingSecondarySender(SendEmailRequest request) {
        secondaryEmailServiceProvider.sendEmail(sender, request.getRecipient(), request.getSubject(), request.getContent());
    }

    @SuppressWarnings("unused")
    public void reportServiceUnavailable(SendEmailRequest request) {
        throw new ServiceUnavailableException();
    }

}
