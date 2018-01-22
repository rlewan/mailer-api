package com.github.rlewan.mailer.emailsenders;

public interface EmailSender {

    void sendEmail(String sender, String recipient, String subject, String text);

}
