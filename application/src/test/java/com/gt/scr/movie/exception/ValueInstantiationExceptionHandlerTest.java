package com.gt.scr.movie.exception;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.fasterxml.jackson.databind.type.TypeFactory;
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
class ValueInstantiationExceptionHandlerTest {

    private ValueInstantiationExceptionHandler valueInstantiationExceptionHandler;

    @Mock
    private ErrorResponseHelper errorResponseHelper;

    @BeforeEach
    void setUp() {
        valueInstantiationExceptionHandler = new ValueInstantiationExceptionHandler(errorResponseHelper);
        when(errorResponseHelper.errorResponse(anyInt(), anyString()))
                .thenAnswer((Answer<Mono<ErrorResponse>>) invocation -> {
                    int statusCode = invocation.getArgument(0);
                    String argument = invocation.getArgument(1);
                    return Mono.just(new ErrorResponse(statusCode, argument));
                });
    }

    @Test
    void shouldHandleValueInstantiationException() throws Exception {
        String message = "Test";
        JsonParser parser = new JsonFactory().createParser(message);

        Mono<ErrorResponse> errorResponseMono =
                valueInstantiationExceptionHandler.handle(ValueInstantiationException.from(parser, message, TypeFactory.unknownType()));

        StepVerifier.create(errorResponseMono)
                .expectNext(new ErrorResponse(400, "Test\n" +
                        " at [Source: (String)\"Test\"; line: 1, column: 0]"))
                .verifyComplete();
    }
}