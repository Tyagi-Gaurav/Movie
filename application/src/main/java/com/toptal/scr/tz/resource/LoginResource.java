package com.toptal.scr.tz.resource;

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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginResource {
    private static final Logger LOG = LoggerFactory.getLogger(LoginResource.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "/user/login",
            consumes = "application/vnd.login.v1+json",
            produces = "application/vnd.login.v1+json")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
        try {
            User user = authenticate(loginRequestDTO);
            final String token = JwtTokenUtil.generateToken(user);
            ImmutableLoginResponseDTO response = ImmutableLoginResponseDTO.builder()
                    .token(token)
                    .id(user.id())
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private User authenticate(LoginRequestDTO request) throws Exception {
        User user = null;

        try {
            LOG.info("Authenticating user: {}, {}", request.userName(), request.password());
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.userName(), request.password()));
            user = (User)auth.getPrincipal();
        } catch (DisabledException e) {
            LOG.error(e.getMessage(), e);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            LOG.error(e.getMessage(), e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        return user;
    }
}
