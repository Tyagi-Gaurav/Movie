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
class IllegalArgumentExceptionHandlerTest {

    @Mock
    private ErrorResponseHelper errorResponseHelper;

    private IllegalArgumentExceptionHandler illegalArgumentExceptionHandler;

    @BeforeEach
    void setUp() {
        illegalArgumentExceptionHandler = new IllegalArgumentExceptionHandler(errorResponseHelper);
        when(errorResponseHelper.errorResponse(anyInt(), anyString()))
                .thenAnswer((Answer<Mono<ErrorResponse>>) invocation -> {
                    int statusCode = invocation.getArgument(0);
                    String argument = invocation.getArgument(1);
                    return Mono.just(new ErrorResponse(statusCode, argument));
                });
    }

    @Test
    void shouldHandleIllegalArgumentException()  {
        String exceptionMessage = "Illegal Argument";
        Mono<ErrorResponse> errorResponse =
                illegalArgumentExceptionHandler.handle(new IllegalArgumentException(exceptionMessage));

        StepVerifier.create(errorResponse)
                .expectNext(new ErrorResponse(400, exceptionMessage))
                .expectComplete();
    }
}