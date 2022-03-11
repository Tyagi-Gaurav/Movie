package com.gt.scr.movie.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(value = -2)
@ControllerAdvice
public class GlobalExceptionHandler implements WebExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        LOG.error(ex.getMessage(), ex);
        HttpStatus statusCode = exchange.getResponse().getStatusCode();
        if (ex instanceof ResponseStatusException responseStatusException) {
            exchange.getResponse()
                    .setStatusCode(responseStatusException.getStatus());
        } else if (statusCode == null || statusCode.is2xxSuccessful()) {
            LOG.error("No/Invalid status code found for Validation Exception {}", statusCode);
            exchange.getResponse()
                    .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            LOG.info("Error occurred with status code: {}", statusCode);
        }

        return exchange.getResponse().setComplete();
    }
}
