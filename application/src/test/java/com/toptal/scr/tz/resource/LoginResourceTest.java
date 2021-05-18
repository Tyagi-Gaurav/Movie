package com.toptal.scr.tz.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptal.scr.tz.config.AuthConfig;
import com.toptal.scr.tz.exception.ApplicationAuthenticationExceptionHandler;
import com.toptal.scr.tz.exception.ErrorResponseHelper;
import com.toptal.scr.tz.resource.domain.ImmutableLoginRequestDTO;
import com.toptal.scr.tz.resource.domain.LoginRequestDTO;
import com.toptal.scr.tz.resource.domain.LoginResponseDTO;
import com.toptal.scr.tz.service.domain.User;
import com.toptal.scr.tz.util.TestBuilders;
import com.toptal.scr.tz.util.TestUtils;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(LoginResourceTest.TestLoginResourceConfiguration.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = {LoginResource.class, ApplicationAuthenticationExceptionHandler.class})
@EnableWebMvc
@ActiveProfiles("LoginResourceTest")
public class LoginResourceTest {

    @Autowired
    private MockMvc mockMvc;

    private LoginRequestDTO loginRequestDTO;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private AuthConfig authConfig;

    @Autowired
    private Key signingKey;

    @MockBean
    private ErrorResponseHelper errorResponseHelper;

    @MockBean
    private Authentication authentication;
    private User user;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String SECRET = "F318AA923A2FAE19833E18AE7A52E867C8E7224472F4FFB277543A6197";

    @BeforeEach
    void setUp() {
        loginRequestDTO = ImmutableLoginRequestDTO.builder()
                .userName(RandomStringUtils.random(8))
                .password(RandomStringUtils.random(8)).build();

        user = TestBuilders.aUser();
        when(authentication.getPrincipal()).thenReturn(user);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authConfig.tokenDuration()).thenReturn(Duration.ofMillis(1000));
        when(authConfig.secret()).thenReturn(SECRET);
    }

    @Test
    void shouldLoginUserAndReturnToken() throws Exception {
        String content = TestUtils.asJsonString(loginRequestDTO);

        //when
        MvcResult mvcResult = mockMvc.perform(post("/user/login")
                .content(content)
                .contentType("application/vnd.login.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        assertThat(mvcResult).isNotNull();
        MockHttpServletResponse response = mvcResult.getResponse();
        LoginResponseDTO loginResponseDTO = objectMapper.readValue(response.getContentAsString(), LoginResponseDTO.class);
        assertThat(loginResponseDTO.token()).isNotEmpty();
        assertThat(loginResponseDTO.id()).isEqualTo(user.id());

        ArgumentCaptor<UsernamePasswordAuthenticationToken> argCaptor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(authenticationManager).authenticate(argCaptor.capture());

        UsernamePasswordAuthenticationToken valueFromToken = argCaptor.getValue();
        assertThat(valueFromToken.getCredentials()).isEqualTo(loginRequestDTO.password());
        assertThat(valueFromToken.getPrincipal()).isEqualTo(loginRequestDTO.userName());
    }

    @TestConfiguration
    public static class TestLoginResourceConfiguration {

        @Bean
        @Profile("LoginResourceTest")
        public Key signingKey() {
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
            return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        }
    }
}

