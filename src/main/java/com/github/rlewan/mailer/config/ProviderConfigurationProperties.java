package com.github.rlewan.mailer.config;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties(prefix = "provider-configuration")
public class ProviderConfigurationProperties {

    @NotBlank
    private String sendgridApiKey;

    @NotBlank
    private String mjApiKey;
    @NotBlank
    private String mjApiSecret;

    public String getSendgridApiKey() {
        return sendgridApiKey;
    }

    public void setSendgridApiKey(String sendgridApiKey) {
        this.sendgridApiKey = sendgridApiKey;
    }

    public String getMjApiKey() {
        return mjApiKey;
    }

    public void setMjApiKey(String mjApiKey) {
        this.mjApiKey = mjApiKey;
    }

    public String getMjApiSecret() {
        return mjApiSecret;
    }

    public void setMjApiSecret(String mjApiSecret) {
        this.mjApiSecret = mjApiSecret;
    }

}
