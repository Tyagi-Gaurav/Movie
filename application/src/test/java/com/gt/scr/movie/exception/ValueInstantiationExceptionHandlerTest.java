package com.gt.scr.movie.exception;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = {ValueInstantiationExceptionHandlerTest.TestValueInstantiationResource.class,
        ValueInstantiationExceptionHandler.class})
@ActiveProfiles("ValueInstantiationExceptionHandlerTest")
class ValueInstantiationExceptionHandlerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ErrorResponseHelper errorResponseHelper;

    @BeforeEach
    void setUp() {
        when(errorResponseHelper.errorResponse(anyInt(), anyString()))
                .thenAnswer((Answer<ResponseEntity<String>>) invocation -> {
                    Integer statusCode = invocation.getArgument(0);
                    String argument = invocation.getArgument(1);
                    return ResponseEntity.status(statusCode)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(String.format("{\"message\":\"%s\"}", argument));
                });
    }

    @Test
    void shouldHandleValueInstantiationException() throws Exception {
        mvc.perform(get("/exception/throw")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ControllerAdvice
    @RestController
    @Profile("ValueInstantiationExceptionHandlerTest")
    static class TestValueInstantiationResource {
        @GetMapping("/exception/throw")
        public void getException() throws IOException {
            String message = "Test";
            JsonParser parser = new JsonFactory().createParser(message);
            throw ValueInstantiationException.from(parser, message, TypeFactory.unknownType());
        }
    }
}