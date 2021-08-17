package com.gt.scr.movie.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
@Order(value = 1)
public class IllegalArgumentExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(IllegalArgumentExceptionHandler.class);

    @Autowired
    private ErrorResponseHelper errorResponseHelper;

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<String> handle(IllegalArgumentException exception) {
        LOG.error("Illegal Argument Handler: " + exception.getMessage(), exception);
        return errorResponseHelper.errorResponse(400, exception.getMessage());
    }
}
