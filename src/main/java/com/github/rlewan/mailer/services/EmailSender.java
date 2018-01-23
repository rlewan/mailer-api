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
public class EmailSender {

    private final String fromAddress;
    private final EmailServiceProvider primaryEmailServiceProvider;
    private final EmailServiceProvider secondaryEmailServiceProvider;

    @Autowired
    public EmailSender(
        @Value("${mailer.from_address}") String fromAddress,
        @Qualifier("primaryEmailServiceProvider") EmailServiceProvider primaryEmailServiceProvider,
        @Qualifier("secondaryEmailServiceProvider") EmailServiceProvider secondaryEmailServiceProvider
    ) {
        this.fromAddress = fromAddress;
        this.primaryEmailServiceProvider = primaryEmailServiceProvider;
        this.secondaryEmailServiceProvider = secondaryEmailServiceProvider;
    }

    @HystrixCommand(fallbackMethod = "sendEmailUsingSecondarySender")
    public void sendEmail(SendEmailRequest request) {
        primaryEmailServiceProvider.sendEmail(fromAddress, request.getRecipient(), request.getSubject(), request.getContent());
    }

    @SuppressWarnings("unused")
    @HystrixCommand(fallbackMethod = "reportServiceUnavailable")
    public void sendEmailUsingSecondarySender(SendEmailRequest request) {
        secondaryEmailServiceProvider.sendEmail(fromAddress, request.getRecipient(), request.getSubject(), request.getContent());
    }

    @SuppressWarnings("unused")
    public void reportServiceUnavailable(SendEmailRequest request) {
        throw new ServiceUnavailableException();
    }

}
