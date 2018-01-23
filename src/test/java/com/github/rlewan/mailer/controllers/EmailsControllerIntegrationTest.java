package com.github.rlewan.mailer.controllers;

import com.github.rlewan.mailer.services.EmailService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmailsControllerIntegrationTest {

    @Autowired
    private MockMvc webClient;

    @MockBean
    private EmailService emailService;

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

}
