package com.gt.scr.movie.exception;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
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
public class ValueInstantiationExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ValueInstantiationExceptionHandler.class);

    @Autowired
    private ErrorResponseHelper errorResponseHelper;

    @ExceptionHandler(value = {ValueInstantiationException.class})
    public ResponseEntity<String> handle(ValueInstantiationException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error("Value Instantiation Exception: " + exception.getMessage(), exception);
        }
        return errorResponseHelper.errorResponse(400, exception.getMessage());
    }
}
