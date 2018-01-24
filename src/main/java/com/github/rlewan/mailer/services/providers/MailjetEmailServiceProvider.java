package com.github.rlewan.mailer.services.providers;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("secondaryEmailServiceProvider")
public class MailjetEmailServiceProvider implements EmailServiceProvider {

    private static final Logger log = LoggerFactory.getLogger(MailjetEmailServiceProvider.class);

    private final MailjetClient mailjetClient;

    @Autowired
    public MailjetEmailServiceProvider(MailjetClient mailjetClient) {
        this.mailjetClient = mailjetClient;
    }

    @Override
    public int sendEmail(String sender, String recipient, String subject, String text) throws EmailServiceProviderException {
        JSONObject message = new JSONObject();
        message.put(Emailv31.Message.FROM, new JSONObject()
            .put(Emailv31.Message.EMAIL, sender)
        )
            .put(Emailv31.Message.SUBJECT, subject)
            .put(Emailv31.Message.TEXTPART, text)
            .put(Emailv31.Message.TO, new JSONArray()
                .put(new JSONObject()
                    .put(Emailv31.Message.EMAIL, recipient)));

        MailjetRequest email = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, (new JSONArray()).put(message));

        try {
            MailjetResponse response = mailjetClient.post(email);
            return response.getStatus();
        } catch (MailjetException | MailjetSocketTimeoutException e) {
            log.error("An error occurred while sending via Mailjet", e);
            throw new EmailServiceProviderException(e);
        }
    }

}
