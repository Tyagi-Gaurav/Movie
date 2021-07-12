package com.gt.scr.movie.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.movie.metrics.ExceptionCounter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ErrorResponseHelper.class)
class ErrorResponseHelperTest {
    @Autowired
    private ErrorResponseHelper errorResponseHelper;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private ExceptionCounter exceptionCounter;

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
        when(objectMapper.writeValueAsString(any())).thenReturn(expectedMessage);

        //when
        ResponseEntity<String> responseEntity = errorResponseHelper.errorResponse(statusCode, "Exception occurred");

        //then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(statusCode);

        assertThat(responseEntity.getBody()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnRuntimeExceptionWhenJsonProcessingFails() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        //when
        Throwable throwable = catchThrowableOfType(
                () -> errorResponseHelper.errorResponse(400, "Exception occurred"),
                RuntimeException.class);

        //then
        assertThat(throwable).isInstanceOf(RuntimeException.class);
    }
}