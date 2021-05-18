package com.toptal.scr.tz.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class IllegalArgumentExceptionHandler {

    @Autowired
    private ErrorResponseHelper errorResponseHelper;

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<String> handle(IllegalArgumentException illegalArgumentException) {
        return errorResponseHelper.errorResponse(400, illegalArgumentException.getMessage());

    }
}
