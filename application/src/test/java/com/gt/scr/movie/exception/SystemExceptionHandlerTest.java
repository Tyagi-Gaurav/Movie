package com.gt.scr.movie.exception;

import com.gt.scr.movie.resource.domain.ErrorResponse;
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
                    String argument = invocation.getArgument(1);
                    return Mono.just(new ErrorResponse(argument));
                });
    }

    @Test
    void shouldHandleRuntimeException() {
        Mono<ErrorResponse> errorResponseMono = systemExceptionHandler.handleException(new RuntimeException());

        StepVerifier.create(errorResponseMono)
                .expectNext(new ErrorResponse("Unexpected error occurred"));
    }

    @Test
    void shouldHandleException() {
        Mono<ErrorResponse> errorResponseMono = systemExceptionHandler.handleException(new Exception());

        StepVerifier.create(errorResponseMono)
                .expectNext(new ErrorResponse("Unexpected error occurred"));
    }

    @Test
    void shouldHandleOtherCustomException() {
        Mono<ErrorResponse> errorResponseMono = systemExceptionHandler.handleException(new IllegalCallerException());

        StepVerifier.create(errorResponseMono)
                .expectNext(new ErrorResponse("Unexpected error occurred"));
    }
}
