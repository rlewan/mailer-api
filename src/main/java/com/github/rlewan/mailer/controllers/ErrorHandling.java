package com.github.rlewan.mailer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandling {

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Validation failed")
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        // Nothing to do
    }

}
