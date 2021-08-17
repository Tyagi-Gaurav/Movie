package com.gt.scr.movie.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class SystemExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SystemExceptionHandler.class);

    @Autowired
    private ErrorResponseHelper errorResponseHelper;

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<String> handleRuntime(RuntimeException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
        return errorResponseHelper.errorResponse(500, "Unexpected error occurred");
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> handleException(Exception exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
        return errorResponseHelper.errorResponse(500, "Unexpected error occurred");
    }
}
