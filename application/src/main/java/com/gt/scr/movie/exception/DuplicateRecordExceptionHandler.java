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
public class DuplicateRecordExceptionHandler {
    public static final Logger LOG = LoggerFactory.getLogger(DuplicateRecordExceptionHandler.class);

    @Autowired
    private ErrorResponseHelper errorResponseHelper;

    @ExceptionHandler(value = {DuplicateRecordException.class})
    public ResponseEntity<String> handle(DuplicateRecordException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
        return errorResponseHelper.errorResponse(403, exception.getMessage());
    }
}
