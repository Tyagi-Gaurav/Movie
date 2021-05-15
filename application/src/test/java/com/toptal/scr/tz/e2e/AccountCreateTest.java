package com.toptal.scr.tz.e2e;

import com.toptal.scr.tz.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

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
class AccountCreateTest {
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

    @Test
    void createUserAccount() throws Exception {
        mockMvc.perform(post("/account/create")
                .contentType("application/vnd+account.create.v1+json"))
                .andExpect(status().isNoContent());
    }
}