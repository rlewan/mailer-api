package com.github.rlewan.mailer.services.providers;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Qualifier("primaryEmailServiceProvider")
public class SendgridEmailServiceProvider implements EmailServiceProvider {

    private static final Logger log = LoggerFactory.getLogger(SendgridEmailServiceProvider.class);

    private final SendGrid sendGrid;

    @Autowired
    public SendgridEmailServiceProvider(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    @Override
    public int sendEmail(String sender, String recipient, String subject, String text) throws EmailServiceProviderException {
        try {
            Request request = prepareSendgridRequest(sender, recipient, subject, text);
            Response response = sendGrid.api(request);
            return response.getStatusCode();
        } catch (IOException e) {
            log.error("An error occurred while sending via Sendgrid", e);
            throw new EmailServiceProviderException(e);
        }
    }

    private Request prepareSendgridRequest(String sender, String recipient, String subject, String text) throws IOException {
        Email from = new Email(sender);
        Email to = new Email(recipient);
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        return request;
    }

}
