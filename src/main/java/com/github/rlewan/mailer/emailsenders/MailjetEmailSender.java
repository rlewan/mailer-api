package com.github.rlewan.mailer.emailsenders;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class MailjetEmailSender implements EmailSender {

    @Override
    public void sendEmail(String sender, String recipient, String subject, String text) {
        MailjetClient client = new MailjetClient(System.getenv("MJ_APIKEY_PUBLIC"), System.getenv("MJ_APIKEY_PRIVATE"), new ClientOptions("v3.1"));

        JSONObject message = new JSONObject();
        message.put(Emailv31.Message.FROM, new JSONObject()
            .put(Emailv31.Message.EMAIL, sender)
            .put(Emailv31.Message.NAME, "Rafał Lewandowski")
        )
            .put(Emailv31.Message.SUBJECT, subject)
            .put(Emailv31.Message.TEXTPART, text)
            .put(Emailv31.Message.TO, new JSONArray()
                .put(new JSONObject()
                    .put(Emailv31.Message.EMAIL, recipient)));

        MailjetRequest email = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, (new JSONArray()).put(message));

        try {
            client.post(email);
        } catch (MailjetException | MailjetSocketTimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
