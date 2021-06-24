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
public class ApplicationAuthenticationExceptionHandler {
    public static final Logger LOG = LoggerFactory.getLogger(ApplicationAuthenticationExceptionHandler.class);

    @Autowired
    private ErrorResponseHelper errorResponseHelper;

    @ExceptionHandler(value = {ApplicationAuthenticationException.class})
    public ResponseEntity<String> handle(ApplicationAuthenticationException exception) {
        LOG.error(exception.getMessage(), exception);
        return errorResponseHelper.errorResponse(401, exception.getMessage());
    }
}
