package com.github.rlewan.mailer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.SERVICE_UNAVAILABLE,
    reason = "Downstream services are not available at the moment"
)
public class ServiceUnavailableException extends RuntimeException {
}
