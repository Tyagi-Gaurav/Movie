package com.toptal.scr.tz.resource;

import com.toptal.scr.tz.resource.domain.ImmutableLoginRequestDTO;
import com.toptal.scr.tz.resource.domain.LoginRequestDTO;
import com.toptal.scr.tz.resource.domain.LoginResponseDTO;
import com.toptal.scr.tz.resource.domain.TestDTO;
import com.toptal.scr.tz.util.TestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = LoginResource.class)
class LoginResourceTest {

    @Autowired
    private MockMvc mockMvc;

    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    void setUp() {
        loginRequestDTO = ImmutableLoginRequestDTO.builder()
                .userName(RandomStringUtils.random(8))
                .password(RandomStringUtils.random(8)).build();
    }

    void shouldLoginUserAndReturnToken() throws Exception {
        String content = TestUtils.asJsonString(loginRequestDTO);
        MvcResult mvcResult = mockMvc.perform(post("/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult).isNotNull();
        ResponseEntity responseEntity = (ResponseEntity) mvcResult.getAsyncResult();
        LoginResponseDTO loginResponseDTO = (LoginResponseDTO) responseEntity.getBody();
        assertThat(loginResponseDTO.token()).isNotEmpty();
    }
}