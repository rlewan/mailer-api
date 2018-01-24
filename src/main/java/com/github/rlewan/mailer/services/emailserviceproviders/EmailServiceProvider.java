package com.github.rlewan.mailer.services.emailserviceproviders;

/**
 * An abstraction for different email sending API providers.
 */
public interface EmailServiceProvider {

    /**
     * <p>Sends an email message.</p>
     * <p>Implementations should propagate any exceptions thrown by API client
     * by wrapping them in {@link EmailServiceProviderException}.</p>
     *
     * @param sender sender email address
     * @param recipient recipient email address
     * @param subject email message subject
     * @param text email message content
     * @return returns the response code of provider API call
     */
    int sendEmail(String sender, String recipient, String subject, String text) throws EmailServiceProviderException;

}
