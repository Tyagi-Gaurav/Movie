package com.gt.scr.movie.exception;

import com.gt.scr.domain.ErrorResponse;
import com.gt.scr.exception.ErrorResponseHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
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
                .expectNext(new ErrorResponse(500, "Unexpected error occurred"))
                .verifyComplete();
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
                .expectNext(new ErrorResponse(500, "Unexpected error occurred"))
                .verifyComplete();;
    }

    @Test
    void shouldHandleUnexpectedSystemException() {
        Mono<ErrorResponse> errorResponseMono = systemExceptionHandler.handleException(new UnexpectedSystemException());

        StepVerifier.create(errorResponseMono)
                .expectNext(new ErrorResponse(500, "Unexpected error occurred"))
                .verifyComplete();;
    }
}
