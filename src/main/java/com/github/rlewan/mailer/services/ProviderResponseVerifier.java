package com.github.rlewan.mailer.services;

import com.github.rlewan.mailer.services.providers.EmailServiceProviderException;
import org.springframework.stereotype.Service;

@Service
public class ProviderResponseVerifier {

    public void assertResponseIsSuccessful(int providerResponseCode) {
        if (providerResponseCode < 200 || providerResponseCode > 299) {
            throw new EmailServiceProviderException(providerResponseCode);
        }
    }

}
