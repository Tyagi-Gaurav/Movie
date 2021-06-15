package com.toptal.scr.tz.resource;

import com.toptal.scr.tz.config.AuthConfig;
import com.toptal.scr.tz.exception.ApplicationAuthenticationException;
import com.toptal.scr.tz.filter.JwtTokenUtil;
import com.toptal.scr.tz.resource.domain.ImmutableLoginResponseDTO;
import com.toptal.scr.tz.resource.domain.LoginRequestDTO;
import com.toptal.scr.tz.resource.domain.LoginResponseDTO;
import com.toptal.scr.tz.service.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;

@RestController
public class LoginResource {
    private static final Logger LOG = LoggerFactory.getLogger(LoginResource.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private Key signingKey;

    @PostMapping(path = "/user/login",
            consumes = "application/vnd.login.v1+json",
            produces = "application/vnd.login.v1+json")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
        try {
            LOG.info("Login user request");
            User user = authenticate(loginRequestDTO);
            final String token = JwtTokenUtil.generateToken(user, authConfig.tokenDuration(), signingKey);
            ImmutableLoginResponseDTO response = ImmutableLoginResponseDTO.builder()
                    .token(token)
                    .id(user.id())
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    private User authenticate(LoginRequestDTO request) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.userName(), request.password()));
            return (User)auth.getPrincipal();
        } catch (AuthenticationException e) {
            LOG.error(e.getMessage(), e);
            throw new ApplicationAuthenticationException("Authentication failed", e);
        }
    }
}
