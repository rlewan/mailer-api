package com.github.rlewan.mailer.config;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.sendgrid.SendGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderClientConfiguration {

    @Bean
    public MailjetClient mailjetClient(ProviderConfigurationProperties properties) {
        return new MailjetClient(
            properties.getMjApiKey(),
            properties.getMjApiSecret(),
            new ClientOptions("v3.1")
        );
    }

    @Bean
    public SendGrid sendGrid(ProviderConfigurationProperties properties) {
        return new SendGrid(properties.getSendgridApiKey());
    }

}
