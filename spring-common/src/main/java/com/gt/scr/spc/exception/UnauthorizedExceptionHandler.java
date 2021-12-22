package com.gt.scr.spc.exception;

import com.gt.scr.domain.ErrorResponse;
import com.gt.scr.exception.ErrorResponseHelper;
import com.gt.scr.exception.UnauthorizedException;
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
public class UnauthorizedExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(UnauthorizedExceptionHandler.class);

    private final ErrorResponseHelper errorResponseHelper;

    public UnauthorizedExceptionHandler(ErrorResponseHelper errorResponseHelper) {
        this.errorResponseHelper = errorResponseHelper;
    }

    @ResponseStatus(code= HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {UnauthorizedException.class})
    public Mono<ErrorResponse> handle(UnauthorizedException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
        return errorResponseHelper.errorResponse(403, "Unauthorised");
    }
}
