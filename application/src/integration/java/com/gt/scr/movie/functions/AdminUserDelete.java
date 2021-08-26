package com.gt.scr.movie.functions;

import com.gt.scr.movie.resource.domain.LoginResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class AdminUserDelete
        implements Interaction<MockMvc, LoginResponseDTO, String, MvcResult> {

    @Override
    public MvcResult apply(MockMvc mockMvc,
                           LoginResponseDTO loginResponseDTO,
                           String userId) {

        try {
            return mockMvc.perform(delete("/user/manage")
                    .param("userId", userId)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", loginResponseDTO.token()))
                    .contentType("application/vnd.user.delete.v1+json"))
                    .andReturn();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
