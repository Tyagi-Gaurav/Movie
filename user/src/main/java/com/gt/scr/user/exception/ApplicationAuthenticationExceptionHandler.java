package com.gt.scr.user.exception;

import com.gt.scr.domain.ErrorResponse;
import com.gt.scr.exception.ErrorResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Order(value = 1)
@Component
public class ApplicationAuthenticationExceptionHandler {
    public static final Logger LOG = LoggerFactory.getLogger(ApplicationAuthenticationExceptionHandler.class);

    private final ErrorResponseHelper errorResponseHelper;

    public ApplicationAuthenticationExceptionHandler(ErrorResponseHelper errorResponseHelper) {
        this.errorResponseHelper = errorResponseHelper;
    }

    @ResponseStatus(code= HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {ApplicationAuthenticationException.class})
    public Mono<ErrorResponse> handle(ApplicationAuthenticationException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
        return errorResponseHelper.errorResponse(401, exception.getMessage());
    }
}
