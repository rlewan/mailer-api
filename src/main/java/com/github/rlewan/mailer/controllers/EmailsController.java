package com.github.rlewan.mailer.controllers;

import com.github.rlewan.mailer.services.EmailSender;
import com.github.rlewan.mailer.model.SendEmailRequest;
import com.github.rlewan.mailer.model.SendEmailResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api
@RestController
@RequestMapping(
    value = "/emails",
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class EmailsController {

    private final EmailSender emailSender;

    @Autowired
    public EmailsController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping
    @ApiOperation("Dispatches an email message to one of downstream providers")
    @ApiResponses({
        @ApiResponse(code = 202, message = "Your request has been dispatched for sending"),
        @ApiResponse(code = 400, message = "Your request was not well formed (perhaps body was missing?)"),
        @ApiResponse(code = 422, message = "The request was well formed, but did not pass validation"),
        @ApiResponse(code = 503, message = "No downstream providers are available to handle your request at this moment")
    })
    public ResponseEntity<SendEmailResponse> sendEmail(@Valid @RequestBody SendEmailRequest request) {
        SendEmailResponse response = emailSender.sendEmail(request);
        return ResponseEntity
            .accepted()
            .body(response);
    }

}
