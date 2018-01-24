package com.github.rlewan.mailer.services.providers;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Qualifier("primaryEmailServiceProvider")
public class SendgridEmailServiceProvider implements EmailServiceProvider {

    private final SendGrid sendGrid;

    @Autowired
    public SendgridEmailServiceProvider(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    @Override
    public int sendEmail(String sender, String recipient, String subject, String text) throws EmailServiceProviderException {
        Email from = new Email(sender);
        Email to = new Email(recipient);
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            return response.getStatusCode();
        } catch (IOException ex) {
            throw new EmailServiceProviderException(ex);
        }
    }

}
