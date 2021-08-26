package com.gt.scr.movie.functions;

import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import com.gt.scr.movie.util.TestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.function.BiFunction;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class Login
        implements BiFunction<MockMvc, LoginRequestDTO, MvcResult> {

    @Override
    public MvcResult apply(MockMvc mockMvc, LoginRequestDTO loginRequestDTO) {
        try {
            return mockMvc.perform(post("/user/login")
                    .content(TestUtils.asJsonString(loginRequestDTO))
                    .contentType("application/vnd.login.v1+json"))
                    .andReturn();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
