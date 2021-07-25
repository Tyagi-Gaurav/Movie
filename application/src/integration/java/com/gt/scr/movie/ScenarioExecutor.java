package com.gt.scr.movie;

import com.gt.scr.movie.functions.LoginFunction;
import com.gt.scr.movie.functions.UserCreateFunction;
import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class ScenarioExecutor {
    private MockMvc mockMvc;
    private MvcResult mvcResult;

    public ScenarioExecutor(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ScenarioExecutor when() {
        return this;
    }

    public ScenarioExecutor and() {
        return this;
    }

    public ScenarioExecutor then() {
        return this;
    }

    public ScenarioExecutor userIsCreatedWith(AccountCreateRequestDTO accountCreateRequestDTO) {
        this.mvcResult = new UserCreateFunction().apply(mockMvc, accountCreateRequestDTO);
        return this;
    }

    public ScenarioExecutor statusIs(int expectedStatus) {
        assertThat(mvcResult.getResponse()).isNotNull();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(expectedStatus);
        return this;
    }

    public ScenarioExecutor userLoginsWith(LoginRequestDTO loginRequestDTO) {
        this.mvcResult = new LoginFunction().apply(mockMvc, loginRequestDTO);
        return this;
    }
}
