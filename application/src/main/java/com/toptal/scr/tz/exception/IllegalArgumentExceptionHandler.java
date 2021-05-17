package com.toptal.scr.tz.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class IllegalArgumentExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<String> handle(IllegalArgumentException illegalArgumentException) {
        return ResponseEntity.badRequest()
                .body(ErrorResponseHelper.errorResponse(illegalArgumentException.getMessage()));

    }
}
