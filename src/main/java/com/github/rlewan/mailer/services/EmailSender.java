package com.github.rlewan.mailer.services;

import com.github.rlewan.mailer.model.SendEmailRequest;
import com.github.rlewan.mailer.model.SendEmailResponse;
import com.github.rlewan.mailer.services.providers.EmailServiceProvider;
import com.github.rlewan.mailer.exceptions.ServiceUnavailableException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    private final String fromAddress;
    private final ProviderResponseVerifier providerResponseVerifier;
    private final EmailServiceProvider primaryEmailServiceProvider;
    private final EmailServiceProvider secondaryEmailServiceProvider;

    @Autowired
    public EmailSender(
        @Value("${mailer.from-address}") String fromAddress,
        ProviderResponseVerifier providerResponseVerifier,
        @Qualifier("primaryEmailServiceProvider") EmailServiceProvider primaryEmailServiceProvider,
        @Qualifier("secondaryEmailServiceProvider") EmailServiceProvider secondaryEmailServiceProvider
    ) {
        this.fromAddress = fromAddress;
        this.providerResponseVerifier = providerResponseVerifier;
        this.primaryEmailServiceProvider = primaryEmailServiceProvider;
        this.secondaryEmailServiceProvider = secondaryEmailServiceProvider;
    }

    @HystrixCommand(fallbackMethod = "sendEmailUsingSecondarySender")
    public SendEmailResponse sendEmail(SendEmailRequest request) {
        return sendEmailViaEmailServiceProvider(primaryEmailServiceProvider, request);
    }

    private SendEmailResponse sendEmailViaEmailServiceProvider(EmailServiceProvider provider, SendEmailRequest request) {
        int providerResponseCode = provider.sendEmail(
            fromAddress,
            request.getRecipient(),
            request.getSubject(),
            request.getContent()
        );
        providerResponseVerifier.assertResponseIsSuccessful(providerResponseCode);
        return SendEmailResponse.ACCEPTED;
    }

    @SuppressWarnings("unused")
    @HystrixCommand(fallbackMethod = "reportServiceUnavailable")
    public SendEmailResponse sendEmailUsingSecondarySender(SendEmailRequest request) {
        return sendEmailViaEmailServiceProvider(secondaryEmailServiceProvider, request);
    }

    @SuppressWarnings("unused")
    public SendEmailResponse reportServiceUnavailable(SendEmailRequest request) {
        throw new ServiceUnavailableException();
    }

}
