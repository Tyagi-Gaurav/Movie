package com.toptal.scr.tz.e2e;

import com.toptal.scr.tz.Application;
import com.toptal.scr.tz.resource.domain.ImmutableAccountCreateRequestDTO;
import com.toptal.scr.tz.resource.domain.ImmutableLoginRequestDTO;
import com.toptal.scr.tz.resource.domain.LoginResponseDTO;
import com.toptal.scr.tz.util.TestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class,
        properties = {
                "management.endpoint.health.enabled=true",
                "management.endpoints.web.exposure.include=*",
                "management.server.port=0",
                "management.port=0"
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@EnableAutoConfiguration
class AccountTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldLoadApplication() {
        assertThat(webApplicationContext).isNotNull();
    }

    void createUserAccount() throws Exception {
        ImmutableAccountCreateRequestDTO requestDTO = ImmutableAccountCreateRequestDTO.builder().build();

        mockMvc.perform(post("/account/create")
                .content(TestUtils.asJsonString(requestDTO))
                .contentType("application/vnd+account.create.v1+json"))
                .andExpect(status().isNoContent());
    }

    void loginUser() throws Exception {
        ImmutableAccountCreateRequestDTO requestDTO =
                ImmutableAccountCreateRequestDTO.builder()
                        .firstName("abc")
                        .lastName("def")
                        .password(RandomStringUtils.random(15))
                        .userName(RandomStringUtils.random(15))
                        .roles(Arrays.asList("USER"))
                        .build();

        mockMvc.perform(post("/account/create")
                .content(TestUtils.asJsonString(requestDTO))
                .contentType("application/vnd+account.create.v1+json"))
                .andExpect(status().isNoContent());

        ImmutableLoginRequestDTO loginRequestDTO = ImmutableLoginRequestDTO.builder()
                .userName(requestDTO.userName())
                .password(requestDTO.password())
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/login")
                .content(TestUtils.asJsonString(loginRequestDTO))
                .contentType("application/vnd+login.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult).isNotNull();
        ResponseEntity responseEntity = (ResponseEntity) mvcResult.getAsyncResult();
        LoginResponseDTO loginResponseDTO = (LoginResponseDTO) responseEntity.getBody();
        assertThat(loginResponseDTO.token()).isNotEmpty();
    }
}