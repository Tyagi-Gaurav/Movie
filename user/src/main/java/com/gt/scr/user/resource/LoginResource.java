package com.gt.scr.user.resource;

import com.gt.scr.domain.User;
import com.gt.scr.user.config.AuthConfig;
import com.gt.scr.user.exception.ApplicationAuthenticationException;
import com.gt.scr.user.resource.domain.LoginRequestDTO;
import com.gt.scr.user.resource.domain.LoginResponseDTO;
import com.gt.scr.user.service.UserService;
import com.gt.scr.util.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Key;

@RestController
public class LoginResource {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthConfig authConfig;
    private final Key signingKey;

    public LoginResource(PasswordEncoder passwordEncoder,
                         UserService userService,
                         AuthConfig authConfig,
                         Key signingKey) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authConfig = authConfig;
        this.signingKey = signingKey;
    }

    @PostMapping(path = "/user/login",
            consumes = "application/vnd.login.v1+json",
            produces = "application/vnd.login.v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return authenticate(loginRequestDTO).map(authUser ->
                new LoginResponseDTO(JwtTokenUtil.generateToken(authUser.username(),
                        authUser.id(),
                        authUser.authorities(),
                        authConfig.tokenDuration(), signingKey),
                        authUser.id()))
                .onErrorResume(Mono::error);
    }

    private Mono<User> authenticate(LoginRequestDTO request) {
        return userService.findByUsername(request.userName())
                .onErrorResume(th -> Mono.error(new ApplicationAuthenticationException(th.getMessage(), th)))
                .filter(user -> passwordEncoder.matches(request.password(), user.getPassword()))
                .map(User.class::cast)
                .switchIfEmpty(Mono.error(new ApplicationAuthenticationException("Invalid User")));
    }
}
