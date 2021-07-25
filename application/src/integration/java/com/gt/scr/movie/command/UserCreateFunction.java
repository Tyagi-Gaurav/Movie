package com.gt.scr.movie.command;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.util.TestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.function.BiFunction;

public class UserCreateFunction
        implements BiFunction<MockMvc, AccountCreateRequestDTO, MvcResult> {

    @Override
    public MvcResult apply(MockMvc mockMvc,
                              AccountCreateRequestDTO accountCreateRequestDTO) {

        try {
            return mockMvc.perform(MockMvcRequestBuilders.post("/user/account/create")
                    .content(TestUtils.asJsonString(accountCreateRequestDTO))
                    .contentType("application/vnd+account.create.v1+json")
                    .accept("application/vnd+account.create.v1+json"))
                    .andReturn();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
