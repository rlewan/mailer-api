package com.github.rlewan.mailer.services.emailserviceproviders;

public interface EmailServiceProvider {

    void sendEmail(String sender, String recipient, String subject, String text);

}
