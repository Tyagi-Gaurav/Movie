package com.gt.scr.movie.resource;

import com.gt.scr.movie.config.AuthConfig;
import com.gt.scr.movie.exception.ApplicationAuthenticationException;
import com.gt.scr.movie.filter.JwtTokenUtil;
import com.gt.scr.movie.resource.domain.ImmutableLoginResponseDTO;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import com.gt.scr.movie.resource.domain.LoginResponseDTO;
import com.gt.scr.movie.service.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            if (LOG.isInfoEnabled()) {
                LOG.info("Login user request");
            }
            var user = authenticate(loginRequestDTO);
            final String token = JwtTokenUtil.generateToken(user, authConfig.tokenDuration(), signingKey);
            ImmutableLoginResponseDTO response = ImmutableLoginResponseDTO.builder()
                    .token(token)
                    .id(user.id())
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
            throw e;
        }
    }

    private User authenticate(LoginRequestDTO request) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.userName(), request.password()));
            return (User)auth.getPrincipal();
        } catch (AuthenticationException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
            throw new ApplicationAuthenticationException("Authentication failed", e);
        }
    }
}
