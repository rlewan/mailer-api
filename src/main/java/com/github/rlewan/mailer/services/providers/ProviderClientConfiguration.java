package com.github.rlewan.mailer.services.providers;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.sendgrid.SendGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderClientConfiguration {

    @Bean
    public MailjetClient mailjetClient() {
        return new MailjetClient(
            System.getenv("MJ_APIKEY_PUBLIC"),
            System.getenv("MJ_APIKEY_PRIVATE"),
            new ClientOptions("v3.1")
        );
    }

    @Bean
    public SendGrid sendGrid() {
        return new SendGrid(System.getenv("SENDGRID_API_KEY"));
    }

}
