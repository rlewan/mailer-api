package com.github.rlewan.mailer.services;

import com.github.rlewan.mailer.exceptions.ServiceUnavailableException;
import com.github.rlewan.mailer.model.SendEmailRequest;
import com.github.rlewan.mailer.model.SendEmailResponse;
import com.github.rlewan.mailer.services.providers.EmailServiceProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailSenderIntegrationTest {

    @MockBean
    @Qualifier("primaryEmailServiceProvider")
    private EmailServiceProvider primaryEmailServiceProvider;

    @MockBean
    @Qualifier("secondaryEmailServiceProvider")
    private EmailServiceProvider secondaryEmailServiceProvider;

    @Mock
    private SendEmailRequest request;

    @Autowired
    private EmailSender emailSender;

    @Test
    public void shouldReturnAcceptedResponseWhenPrimaryProviderSucceeds() {
        mockProviderResponseCode(primaryEmailServiceProvider, 200);

        SendEmailResponse response = emailSender.sendEmail(request);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }

    private void mockProviderResponseCode(EmailServiceProvider provider, int responseCode) {
        when(provider.sendEmail(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(responseCode);
    }

    @Test
    public void shouldReturnAcceptedResponseWhenFallbackToSecondaryProviderSucceeds() {
        mockProviderToThrow(primaryEmailServiceProvider);
        mockProviderResponseCode(secondaryEmailServiceProvider, 200);

        SendEmailResponse response = emailSender.sendEmail(request);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }

    private void mockProviderToThrow(EmailServiceProvider provider) {
        when(provider.sendEmail(anyString(), anyString(), anyString(), anyString()))
            .thenThrow(new RuntimeException("Something went wrong!"));
    }

    @Test(expected = ServiceUnavailableException.class)
    public void shouldThrowServiceUnavailableWhenBothProvidersFailWithException() {
        mockProviderToThrow(primaryEmailServiceProvider);
        mockProviderToThrow(secondaryEmailServiceProvider);

        emailSender.sendEmail(request);
    }

    @Test(expected = ServiceUnavailableException.class)
    public void shouldThrowServiceUnavailableWhenBothProvidersReturnFailureResponseCode() {
        mockProviderResponseCode(primaryEmailServiceProvider, 500);
        mockProviderResponseCode(secondaryEmailServiceProvider, 500);

        emailSender.sendEmail(request);
    }

}
