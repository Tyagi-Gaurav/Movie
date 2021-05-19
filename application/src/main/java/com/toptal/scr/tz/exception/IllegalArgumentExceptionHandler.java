package com.toptal.scr.tz.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class IllegalArgumentExceptionHandler {
    public static final Logger LOG = LoggerFactory.getLogger(IllegalArgumentExceptionHandler.class);


    @Autowired
    private ErrorResponseHelper errorResponseHelper;

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<String> handle(IllegalArgumentException exception) {
        LOG.error(exception.getMessage(), exception);
        return errorResponseHelper.errorResponse(400, exception.getMessage());

    }
}
