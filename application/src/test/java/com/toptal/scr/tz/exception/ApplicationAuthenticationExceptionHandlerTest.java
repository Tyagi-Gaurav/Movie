package com.toptal.scr.tz.exception;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = {ApplicationAuthenticationExceptionHandlerTest.TestResource.class,
        ApplicationAuthenticationExceptionHandler.class})
class ApplicationAuthenticationExceptionHandlerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldHandleAuthenticationException() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/exception/throw")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @ControllerAdvice
    @RestController
    static class TestResource {
        @GetMapping("/exception/throw")
        public void getException() {
            throw new ApplicationAuthenticationException("");
        }
    }
}