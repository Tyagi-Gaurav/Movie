package com.gt.scr.movie.functions;

import com.gt.scr.movie.resource.domain.LoginResponseDTO;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.util.TestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MovieCreate
        implements Interaction<MockMvc,
                LoginResponseDTO,
                MovieCreateRequestDTO, MvcResult> {

    @Override
    public MvcResult apply(MockMvc mockMvc,
                           LoginResponseDTO loginResponseDTO,
                           MovieCreateRequestDTO movieCreateRequestDTO) {
        try {
            return mockMvc.perform(post("/user/movie")
                    .content(TestUtils.asJsonString(movieCreateRequestDTO))
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", loginResponseDTO.token()))
                    .contentType("application/vnd.movie.add.v1+json"))
                    .andExpect(status().isNoContent())
                    .andReturn();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
