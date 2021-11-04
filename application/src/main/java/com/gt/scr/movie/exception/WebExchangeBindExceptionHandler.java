package com.gt.scr.movie.exception;

import com.gt.scr.movie.resource.domain.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Component
@Order(value = 1)
public class WebExchangeBindExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(WebExchangeBindExceptionHandler.class);

    private final ErrorResponseHelper errorResponseHelper;

    public WebExchangeBindExceptionHandler(ErrorResponseHelper errorResponseHelper) {
        this.errorResponseHelper = errorResponseHelper;
    }

    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {WebExchangeBindException.class})
    public Mono<ErrorResponse> handle(WebExchangeBindException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
        return errorResponseHelper.errorResponse(400, exception.getMessage());
    }
}
