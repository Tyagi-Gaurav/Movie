package com.toptal.scr.tz.resource;

import com.toptal.scr.tz.resource.domain.AccountCreateRequestDTO;
import com.toptal.scr.tz.resource.domain.ImmutableAccountCreateRequestDTO;
import com.toptal.scr.tz.service.UserService;
import com.toptal.scr.tz.service.domain.User;
import com.toptal.scr.tz.util.TestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = AccountCreateResource.class)
@EnableWebMvc
public class AccountCreateResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private AccountCreateRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = ImmutableAccountCreateRequestDTO.builder()
                .role("USER")
                .userName(RandomStringUtils.randomAlphabetic(10))
                .password(RandomStringUtils.randomAlphabetic(10))
                .firstName("xsdfdsf")
                .lastName("bsdafdsf")
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(AdditionalAnswers.returnsFirstArg()));
    }

    @Test
    void shouldReturnSuccessForUserCreation() throws Exception {
        //when
        mockMvc.perform(post("/user/account/create")
                .content(TestUtils.asJsonString(requestDTO))
                .contentType("application/vnd+account.create.v1+json")
                .accept("application/vnd+account.create.v1+json"))
                .andExpect(status().isNoContent());

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).add(userArgumentCaptor.capture());

        User user = userArgumentCaptor.getValue();
        assertThat(user.id()).isNotNull();
        assertThat(user.firstName()).isEqualTo(requestDTO.firstName());
        assertThat(user.lastName()).isEqualTo(requestDTO.lastName());
        assertThat(user.getRole()).isEqualTo(requestDTO.role());
    }

    @Test
    void invalidContentTypeShouldReturnMethodNotAllowed() throws Exception {
        mockMvc.perform(post("/user/account/create")
                .content(TestUtils.asJsonString(requestDTO))
                .contentType("application/vnd+account.v1+json"))
                .andExpect(status().isUnsupportedMediaType());
    }
}
