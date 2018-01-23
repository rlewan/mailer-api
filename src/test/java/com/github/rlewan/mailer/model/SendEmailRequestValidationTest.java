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
    public void shouldBeValidWhenSubjectAndContentAreNull() {
        SendEmailRequest request = new SendEmailRequest("user@mail.com", null, null);

        Set<String> validationErrors = validate(request);

        assertThat(validationErrors).isEmpty();
    }

    @Test
    public void shouldBeValidWhenSubjectAndContentAreEmptyStrings() {
        SendEmailRequest request = new SendEmailRequest("user@mail.com", "", "");

        Set<String> validationErrors = validate(request);

        assertThat(validationErrors).isEmpty();
    }

}
