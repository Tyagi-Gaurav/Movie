package com.gt.scr.movie.functions;

import com.gt.scr.movie.resource.domain.LoginResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.function.BiFunction;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RetrieveAllUserMovies implements BiFunction<MockMvc, LoginResponseDTO, MvcResult> {

    @Override
    public MvcResult apply(MockMvc mockMvc, LoginResponseDTO loginResponseDTO) {
        try {
            return mockMvc.perform(get("/user/movie")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", loginResponseDTO.token()))
                    .contentType("application/vnd.movie.read.v1+json"))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
