package com.gt.scr.user.exception;


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
class ApplicationAuthenticationExceptionHandlerTest {
    @Mock
    private ErrorResponseHelper errorResponseHelper;

    private ApplicationAuthenticationExceptionHandler applicationAuthenticationExceptionHandler;

    @BeforeEach
    void setUp() {
        applicationAuthenticationExceptionHandler = new ApplicationAuthenticationExceptionHandler(errorResponseHelper);
        when(errorResponseHelper.errorResponse(anyInt(), anyString()))
                .thenAnswer((Answer<Mono<ErrorResponse>>) invocation -> {
                    int statusCode = invocation.getArgument(0);
                    String argument = invocation.getArgument(1);
                    return Mono.just(new ErrorResponse(statusCode, argument));
                });
    }

    @Test
    void shouldHandleAuthenticationException() throws Exception {
        String exceptionMessage = "Unauthorised";
        Mono<ErrorResponse> handle = applicationAuthenticationExceptionHandler.handle(new ApplicationAuthenticationException(exceptionMessage,
                new IllegalCallerException()));

        StepVerifier.create(handle)
                .expectNext(new ErrorResponse(401, exceptionMessage))
                .expectComplete();
    }
}