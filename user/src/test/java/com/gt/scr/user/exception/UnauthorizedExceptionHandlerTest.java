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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnauthorizedExceptionHandlerTest {
    @Mock
    private ErrorResponseHelper errorResponseHelper;

    private UnauthorizedExceptionHandler unauthorizedExceptionHandler;

    @BeforeEach
    void setUp() {
        unauthorizedExceptionHandler = new UnauthorizedExceptionHandler(errorResponseHelper);
        when(errorResponseHelper.errorResponse(anyInt(), any()))
                .thenAnswer((Answer<Mono<ErrorResponse>>) invocation -> {
                    int statusCode = invocation.getArgument(0);
                    String argument = invocation.getArgument(1);
                    return Mono.just(new ErrorResponse(statusCode, argument));
                });
    }

    @Test
    void shouldHandleException()  {
        Mono<ErrorResponse> errorResponse =
                unauthorizedExceptionHandler.handle(new UnauthorizedException());

        StepVerifier.create(errorResponse)
                .expectNext(new ErrorResponse(403, "Unauthorized"))
                .expectComplete();
    }
}