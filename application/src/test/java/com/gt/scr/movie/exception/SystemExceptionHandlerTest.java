package com.gt.scr.movie.exception;

import com.gt.scr.movie.resource.domain.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SystemExceptionHandlerTest {
    @Mock
    private ErrorResponseHelper errorResponseHelper;

    private SystemExceptionHandler systemExceptionHandler;

    @BeforeEach
    void setUp() {
        systemExceptionHandler = new SystemExceptionHandler(errorResponseHelper);
        when(errorResponseHelper.errorResponse(anyInt(), anyString()))
                .thenAnswer((Answer<Mono<ErrorResponse>>) invocation -> {
                    int statusCode = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return Mono.just(new ErrorResponse(statusCode, message));
                });
    }

    @Test
    void shouldHandleRuntimeException() {
        Mono<ErrorResponse> errorResponseMono = systemExceptionHandler.handleException(new RuntimeException());

        StepVerifier.create(errorResponseMono)
                .expectNext(new ErrorResponse(500, "Unexpected error occurred"));
    }

    @Test
    void shouldHandleException() {
        Mono<ErrorResponse> errorResponseMono = systemExceptionHandler.handleException(new Exception());

        StepVerifier.create(errorResponseMono)
                .expectNext(new ErrorResponse(500, "Unexpected error occurred"));
    }

    @Test
    void shouldHandleOtherCustomException() {
        Mono<ErrorResponse> errorResponseMono = systemExceptionHandler.handleException(new IllegalCallerException());

        StepVerifier.create(errorResponseMono)
                .expectNext(new ErrorResponse(500, "Unexpected error occurred"));
    }

    @Test
    void shouldHandleGeneralValidationException() {
        WebExchangeBindException webExchangeBindException = Mockito.mock(WebExchangeBindException.class);
        Mono<ErrorResponse> errorResponseMono = systemExceptionHandler.handleException(webExchangeBindException);

        StepVerifier.create(errorResponseMono)
                .expectNext(new ErrorResponse(400, "Validation error occurred"));
    }

    @Test
    void shouldHandleUnAuthorizedException() {
        Mono<ErrorResponse> errorResponseMono = systemExceptionHandler.handleException(new UnauthorizedException());

        StepVerifier.create(errorResponseMono)
                .expectNext(new ErrorResponse(403, "Unauthorized"));
    }
}
