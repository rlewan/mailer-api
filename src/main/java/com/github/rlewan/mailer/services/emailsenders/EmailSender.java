package com.github.rlewan.mailer.services.emailsenders;

public interface EmailSender {

    void sendEmail(String sender, String recipient, String subject, String text);

}
