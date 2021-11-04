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
import reactor.core.publisher.Mono;

@ControllerAdvice
@Component
public class SystemExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SystemExceptionHandler.class);

    private final ErrorResponseHelper errorResponseHelper;
    private static final String UNEXPECTED_ERROR_OCCURRED = "Unexpected error occurred";

    public SystemExceptionHandler(ErrorResponseHelper errorResponseHelper) {
        this.errorResponseHelper = errorResponseHelper;
    }

    @ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {RuntimeException.class})
    public Mono<ErrorResponse> handleRuntime(RuntimeException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
        return errorResponseHelper.errorResponse(500, UNEXPECTED_ERROR_OCCURRED);
    }

    @ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public Mono<ErrorResponse> handleException(Exception exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
        return errorResponseHelper.errorResponse(500, UNEXPECTED_ERROR_OCCURRED);
    }
}
