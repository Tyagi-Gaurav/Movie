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
public class DuplicateRecordExceptionHandler {

    public static final Logger LOG = LoggerFactory.getLogger(DuplicateRecordExceptionHandler.class);

    @Autowired
    private ErrorResponseHelper errorResponseHelper;

    @ExceptionHandler(value = {DuplicateRecordException.class})
    public ResponseEntity<String> handle(DuplicateRecordException duplicateRecordException) {
        return errorResponseHelper.errorResponse(403, duplicateRecordException.getMessage());
    }
}
