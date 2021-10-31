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
@Order(value = 1)
public class DuplicateRecordExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DuplicateRecordExceptionHandler.class);

    private final ErrorResponseHelper errorResponseHelper;

    public DuplicateRecordExceptionHandler(ErrorResponseHelper errorResponseHelper) {
        this.errorResponseHelper = errorResponseHelper;
    }

    @ResponseStatus(code= HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {DuplicateRecordException.class})
    public Mono<ErrorResponse> handle(DuplicateRecordException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
        return errorResponseHelper.errorResponse(403, exception.getMessage());
    }
}
