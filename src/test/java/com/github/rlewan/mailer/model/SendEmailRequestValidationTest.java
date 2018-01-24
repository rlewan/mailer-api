package com.github.rlewan.mailer.model;

import org.junit.Test;

import java.util.Set;

import static com.github.rlewan.mailer.model.ModelValidator.validate;
import static org.assertj.core.api.Assertions.assertThat;

public class SendEmailRequestValidationTest {

    @Test
    public void shouldBeValidWhenAllValuesAreValid() {
        SendEmailRequest request = new SendEmailRequest("user@mail.com", "Subject", "Content");
        Set<String> validationErrors = validate(request);
        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void shouldBeInvalidWhenGRecipientIsNull() {
        SendEmailRequest request = new SendEmailRequest(null, "Subject", "Content");
        Set<String> validationErrors = validate(request);
        assertThat(validationErrors).containsOnly("recipient : may not be empty");
    }

    @Test
    public void shouldBeInvalidWhenRecipientIsEmpty() {
        SendEmailRequest request = new SendEmailRequest("", "Subject", "Content");
        Set<String> validationErrors = validate(request);
        assertThat(validationErrors).containsOnly("recipient : may not be empty");
    }

    @Test
    public void shouldBeInvalidWhenRecipientIsNotAnEmail() {
        SendEmailRequest request = new SendEmailRequest("I'm not an email", "Subject", "Content");
        Set<String> validationErrors = validate(request);
        assertThat(validationErrors).containsOnly("recipient : not a well-formed email address");
    }

    @Test
    public void shouldBeInvalidWhenSubjectIsNull() {
        SendEmailRequest request = new SendEmailRequest("user@mail.com", null, "Content");
        Set<String> validationErrors = validate(request);
        assertThat(validationErrors).containsOnly("subject : may not be empty");
    }

    @Test
    public void shouldBeInvalidWhenSubjectIsEmpty() {
        SendEmailRequest request = new SendEmailRequest("user@mail.com", "", "Content");
        Set<String> validationErrors = validate(request);
        assertThat(validationErrors).containsOnly("subject : may not be empty");
    }

    @Test
    public void shouldBeInvalidWhenContentIsNull() {
        SendEmailRequest request = new SendEmailRequest("user@mail.com", "Subject", null);
        Set<String> validationErrors = validate(request);
        assertThat(validationErrors).containsOnly("content : may not be empty");
    }

    @Test
    public void shouldBeInvalidWhenContentIsEmpty() {
        SendEmailRequest request = new SendEmailRequest("user@mail.com", "Subject", "");
        Set<String> validationErrors = validate(request);
        assertThat(validationErrors).containsOnly("content : may not be empty");
    }

}
