package com.gt.scr.movie.command;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class TestExecutor {
    private MockMvc mockMvc;
    private MvcResult mvcResult;

    public TestExecutor(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public TestExecutor when() {
        return this;
    }

    public TestExecutor and() {
        return this;
    }

    public TestExecutor then() {
        return this;
    }

    public TestExecutor userIsCreatedWith(AccountCreateRequestDTO accountCreateRequestDTO) {
        this.mvcResult = new UserCreateFunction().apply(mockMvc, accountCreateRequestDTO);
        return this;
    }

    public TestExecutor statusIs(int expectedStatus) {
        assertThat(mvcResult.getResponse()).isNotNull();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(expectedStatus);
        return this;
    }

    public TestExecutor userLoginsWith(LoginRequestDTO loginRequestDTO) {
        this.mvcResult = new LoginFunction().apply(mockMvc, loginRequestDTO);
        return this;
    }
}
