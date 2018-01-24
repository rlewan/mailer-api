package com.github.rlewan.mailer.services.emailserviceproviders;

public class EmailServiceProviderException extends RuntimeException {

    public EmailServiceProviderException(int providerResponseCode) {
        super("Provider responded with an unexpected response code of " + providerResponseCode);
    }

    public EmailServiceProviderException(Throwable cause) {

    }

}
