package com.gt.scr.movie.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gt.scr.movie.metrics.ExceptionCounter;
import com.gt.scr.movie.resource.domain.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ErrorResponseHelperTest {
    private ErrorResponseHelper errorResponseHelper;

    @Mock
    private ExceptionCounter exceptionCounter;

    @BeforeEach
    void setUp() {
        errorResponseHelper = new ErrorResponseHelper(exceptionCounter);
    }

    @Test
    void shouldIncrementExceptionCounterWhenHandlingException() {
        //given
        int statusCode = 200;

        //when
        errorResponseHelper.errorResponse(statusCode, "Exception occurred");

        //then
        verify(exceptionCounter).increment(statusCode);
    }

    @Test
    void shouldReturnResponseEntityWithErrorCodeAndMessage() throws JsonProcessingException {
        //given
        int statusCode = 200;
        String expectedMessage = "{\"message\" : \"Exception occurred\"}";

        //when
        Mono<ErrorResponse> errorResponseMono = errorResponseHelper.errorResponse(statusCode, "Exception occurred");

        //then
        StepVerifier.create(errorResponseMono)
                .expectNext(new ErrorResponse(statusCode, "Exception occurred"))
                .verifyComplete();
    }
}