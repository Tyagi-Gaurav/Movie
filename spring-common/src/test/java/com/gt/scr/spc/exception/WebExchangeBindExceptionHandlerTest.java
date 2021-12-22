package com.gt.scr.spc.exception;

import com.gt.scr.domain.ErrorResponse;
import com.gt.scr.exception.ErrorResponseHelper;
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
class WebExchangeBindExceptionHandlerTest {
    @Mock
    private ErrorResponseHelper errorResponseHelper;

    private WebExchangeBindExceptionHandler webExchangeBindExceptionHandler;

    @BeforeEach
    void setUp() {
        webExchangeBindExceptionHandler = new WebExchangeBindExceptionHandler(errorResponseHelper);
        when(errorResponseHelper.errorResponse(anyInt(), anyString()))
                .thenAnswer((Answer<Mono<ErrorResponse>>) invocation -> {
                    int statusCode = invocation.getArgument(0);
                    String argument = invocation.getArgument(1);
                    return Mono.just(new ErrorResponse(statusCode, argument));
                });
    }

    @Test
    void shouldHandleException()  {
        WebExchangeBindException mockException = Mockito.mock(WebExchangeBindException.class);
        when(mockException.getMessage()).thenReturn("Validation error occurred");

        Mono<ErrorResponse> errorResponse =
                webExchangeBindExceptionHandler.handle(mockException);

        StepVerifier.create(errorResponse)
                .expectNext(new ErrorResponse(400, "Validation error occurred"))
                .expectComplete();
    }

}