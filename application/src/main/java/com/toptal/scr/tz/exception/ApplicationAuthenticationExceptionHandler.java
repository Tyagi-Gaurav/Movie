package com.toptal.scr.tz.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class ApplicationAuthenticationExceptionHandler {

    @Autowired
    private ErrorResponseHelper errorResponseHelper;

    @ExceptionHandler(value = {ApplicationAuthenticationException.class})
    public ResponseEntity<String> handle(ApplicationAuthenticationException applicationAuthenticationException) {
        return errorResponseHelper.errorResponse(401, applicationAuthenticationException.getMessage());
    }
}
