package com.gt.scr.movie.exception;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.gt.scr.movie.resource.domain.ErrorResponse;
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
@Component
@Order(value = 1)
public class ValueInstantiationExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ValueInstantiationExceptionHandler.class);

    private final ErrorResponseHelper errorResponseHelper;

    public ValueInstantiationExceptionHandler(ErrorResponseHelper errorResponseHelper) {
        this.errorResponseHelper = errorResponseHelper;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ValueInstantiationException.class})
    public Mono<ErrorResponse> handle(ValueInstantiationException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error("Value Instantiation Exception: " + exception.getMessage(), exception);
        }
        return errorResponseHelper.errorResponse(400, exception.getMessage());
    }
}
