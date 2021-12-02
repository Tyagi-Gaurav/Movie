package com.gt.scr.exception;

import com.gt.scr.domain.ErrorResponse;
import com.gt.scr.metrics.ExceptionCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ErrorResponseHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorResponseHelper.class);

    private final ExceptionCounter exceptionCounter;

    public ErrorResponseHelper(ExceptionCounter exceptionCounter) {
        this.exceptionCounter = exceptionCounter;
    }

    public Mono<ErrorResponse> errorResponse(int statusCode, String message) {
        ErrorResponse errorResponse = new ErrorResponse(statusCode, message);

        LOG.error("Response with status code {}", statusCode);

        exceptionCounter.increment(statusCode);

        return Mono.just(errorResponse);
    }
}
