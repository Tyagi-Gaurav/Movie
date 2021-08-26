package com.gt.scr.movie.filter;

import com.gt.scr.movie.service.UserService;
import com.gt.scr.movie.service.domain.User;
import com.gt.scr.movie.util.UserBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Import(AuthenticationFilterTest.TestAuthResourceConfiguration.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AuthenticationFilter.class)
@ActiveProfiles("AuthenticationFilterTest")
class AuthenticationFilterTest {
    @SpyBean
    private AuthenticationFilter authenticationFilter;

    @MockBean
    private HttpServletRequest httpServletRequest;

    @MockBean
    private HttpServletResponse httpServletResponse;

    @MockBean
    private FilterChain filterChain;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    private static final String token = "Bearer eyJhbGciOiJIU";
    private static final String userId = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        when(authenticationFilter.getJwtTokenUtil(anyString())).thenReturn(jwtTokenUtil);
        when(jwtTokenUtil.getUserIdFromToken()).thenReturn(userId);
    }

    @Test
    void shouldAuthenticateValidUser() throws ServletException, IOException {
        //given
        User user = UserBuilder.aUser().build();
        when(httpServletRequest.getHeader("Authorization")).thenReturn(token);
        when(userService.findUserBy(UUID.fromString(userId))).thenReturn(user);
        when(jwtTokenUtil.validateToken(user)).thenReturn(true);
        LinkedHashMap authoritiesMap = new LinkedHashMap();
        authoritiesMap.put("authority", "USER");
        when(jwtTokenUtil.getClaimFromToken(any())).thenReturn(Collections.singletonList(authoritiesMap));

        //when
        authenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        //then
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
        verify(userService).findUserBy(UUID.fromString(userId));
    }

    @TestConfiguration
    public static class TestAuthResourceConfiguration {

        @Bean
        @Profile("AuthenticationFilterTest")
        public Key signingKey() {
            String SECRET = "19CA249C582715657BDCAB1FB31E69F854443A4FE3CBAFFD215E3F3676";
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
            return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        }
    }
}