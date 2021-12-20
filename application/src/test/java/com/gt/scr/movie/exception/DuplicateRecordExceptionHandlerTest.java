package com.gt.scr.movie.exception;


import com.gt.scr.exception.DuplicateRecordException;
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
class DuplicateRecordExceptionHandlerTest {
    @Mock
    private ErrorResponseHelper errorResponseHelper;

    private DuplicateRecordExceptionHandler duplicateRecordExceptionHandler;

    @BeforeEach
    void setUp() {
        duplicateRecordExceptionHandler = new DuplicateRecordExceptionHandler(errorResponseHelper);
        when(errorResponseHelper.errorResponse(anyInt(), anyString()))
                .thenAnswer((Answer<Mono<ErrorResponse>>) invocation -> {
                    int statusCode = invocation.getArgument(0);
                    String argument = invocation.getArgument(1);
                    return Mono.just(new ErrorResponse(statusCode, argument));
                });
    }

    @Test
    void shouldHandleAuthenticationException() {
        String exceptionMessage = "test Message";
        Mono<ErrorResponse> testErrorResponse =
                duplicateRecordExceptionHandler.handle(new DuplicateRecordException(exceptionMessage));

        StepVerifier.create(testErrorResponse)
                .expectNext(new ErrorResponse(403, exceptionMessage))
                .expectComplete();
    }
}