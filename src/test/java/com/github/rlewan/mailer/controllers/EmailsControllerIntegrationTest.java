package com.github.rlewan.mailer.controllers;

import com.github.rlewan.mailer.model.SendEmailRequest;
import com.github.rlewan.mailer.model.SendEmailResponse;
import com.github.rlewan.mailer.services.EmailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmailsControllerIntegrationTest {

    @Autowired
    private MockMvc webClient;

    @MockBean
    private EmailSender emailSender;

    @Test
    public void sendEmailShouldRespondWith422WhenRequestEntityIsInvalid() throws Exception {
        webClient
            .perform(post("/emails")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{ }"))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void sendEmailShouldRespondWith400WhenRequestEntityIsNotProvided() throws Exception {
        webClient
            .perform(post("/emails")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void sendEmailShouldRespondWith202WhenRequestIsProcessedSuccessfully() throws Exception {
        given(emailSender.sendEmail(any(SendEmailRequest.class))).willReturn(SendEmailResponse.ACCEPTED);
        webClient
            .perform(post("/emails")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{ \"recipient\": \"user@mail.com\" }"))
            .andExpect(status().isAccepted());
    }

}
