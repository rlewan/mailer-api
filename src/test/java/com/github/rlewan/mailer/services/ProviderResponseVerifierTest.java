package com.github.rlewan.mailer.services;

import com.github.rlewan.mailer.services.emailserviceproviders.EmailServiceProviderException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ProviderResponseVerifierTest {

    private ProviderResponseVerifier verifier = new ProviderResponseVerifier();

    @Test
    public void shouldNotThrowExceptionForResponseCodesInSuccessRange() {
        Throwable throwable = catchThrowable(() -> {
            for (int responseCode = 200; responseCode < 300; responseCode++) {
                verifier.assertResponseIsSuccessful(responseCode);
            }
        });
        assertThat(throwable).isNull();
    }

    @Test(expected = EmailServiceProviderException.class)
    public void shouldThrowExceptionWhenResponseCodeIs199() {
        verifier.assertResponseIsSuccessful(199);
    }

    @Test(expected = EmailServiceProviderException.class)
    public void shouldThrowExceptionWhenResponseCodeIs300() {
        verifier.assertResponseIsSuccessful(300);
    }

    @Test(expected = EmailServiceProviderException.class)
    public void shouldThrowExceptionWhenResponseCodeIs400() {
        verifier.assertResponseIsSuccessful(400);
    }

    @Test(expected = EmailServiceProviderException.class)
    public void shouldThrowExceptionWhenResponseCodeIs500() {
        verifier.assertResponseIsSuccessful(500);
    }

}
