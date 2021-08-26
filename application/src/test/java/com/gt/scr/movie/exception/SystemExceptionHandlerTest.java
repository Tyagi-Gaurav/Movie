package com.gt.scr.movie.exception;

import com.gt.scr.movie.resource.domain.ErrorResponse;
import com.gt.scr.movie.util.TestUtils;
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
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = {SystemExceptionHandlerTest.TestRuntimeExceptionSource.class,
        SystemExceptionHandler.class})
@ActiveProfiles("ExceptionHandlerTest")
class SystemExceptionHandlerTest {

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
    void shouldHandleRuntimeException() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/runtime/exception/throw")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        ErrorResponse testErrorResponse = TestUtils.readFromString(response.getContentAsString(), ErrorResponse.class);
        assertThat(testErrorResponse.message()).isEqualTo("Unexpected error occurred");
    }

    @Test
    void shouldHandleException() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/exception/throw")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        ErrorResponse testErrorResponse = TestUtils.readFromString(response.getContentAsString(), ErrorResponse.class);
        assertThat(testErrorResponse.message()).isEqualTo("Unexpected error occurred");
    }

    @Test
    void shouldHandleOtherCustomException() throws Exception {
        mvc.perform(get("/exception/throw/methodArgumentNotValid")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ControllerAdvice
    @RestController
    @Profile("ExceptionHandlerTest")
    static class TestRuntimeExceptionSource {
        @GetMapping("/runtime/exception/throw")
        public void getRuntimeException() {
            throw new RuntimeException();
        }

        @GetMapping("/exception/throw")
        public void getException() throws Exception {
            throw new Exception();
        }

        @GetMapping("/exception/throw/methodArgumentNotValid")
        public void methodArgumentNotValidException() throws Exception {
            throw new MethodArgumentNotValidException(
                    new MethodParameter(TestRuntimeExceptionSource.class.getMethod("methodArgumentNotValidException"), -1),
                    new BeanPropertyBindingResult(new TestRuntimeExceptionSource(), ""));
        }
    }
}
