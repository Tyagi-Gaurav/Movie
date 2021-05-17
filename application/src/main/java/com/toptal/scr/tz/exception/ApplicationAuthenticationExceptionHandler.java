package com.toptal.scr.tz.exception;

import com.toptal.scr.tz.resource.domain.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class ApplicationAuthenticationExceptionHandler {

    @ExceptionHandler(value = {ApplicationAuthenticationException.class})
    public ResponseEntity<ErrorResponse> handle() {
        return ResponseEntity.status(401).build();
    }
}
