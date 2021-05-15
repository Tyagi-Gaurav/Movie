package com.toptal.scr.tz.resource;

import com.toptal.scr.tz.resource.domain.ImmutableAccountCreateRequestDTO;
import com.toptal.scr.tz.util.TestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = AccountCreateResource.class)
public class AccountCreateResourceTest {
    @Autowired
    private MockMvc mockMvc;

    private ImmutableAccountCreateRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = ImmutableAccountCreateRequestDTO.builder()
                .roles(Arrays.asList("user"))
                .userName(RandomStringUtils.random(10))
                .password(RandomStringUtils.random(10))
                .firstName("x")
                .lastName("b")
                .build();
    }

    void shouldReturnSuccessForUserCreation() throws Exception {
        mockMvc.perform(post("/account/create")
                .content(TestUtils.asJsonString(requestDTO))
                .contentType("application/vnd+account.create.v1+json"))
                .andExpect(status().isNoContent());
    }

    void invalidContentTypeShouldReturnMethodNotAllowed() throws Exception {
        mockMvc.perform(post("/account/create")
                .content(TestUtils.asJsonString(requestDTO))
                .contentType("application/vnd+account.v1+json"))
                .andExpect(status().isUnsupportedMediaType());
    }
}
