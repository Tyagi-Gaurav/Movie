package com.gt.scr.movie.grpc_resource;

import com.gt.scr.exception.ApplicationAuthenticationException;
import com.gt.scr.spc.domain.User;
import com.gt.scr.movie.config.AuthConfig;
import com.gt.scr.movie.filter.JwtTokenUtil;
import com.gt.scr.movie.grpc.LoginGrpcRequestDTO;
import com.gt.scr.movie.grpc.LoginGrpcResponseDTO;
import com.gt.scr.movie.grpc.LoginServiceGrpc;
import com.gt.scr.movie.service.UserService;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.security.Key;

public class LoginGrpcResource extends LoginServiceGrpc.LoginServiceImplBase {
    private static final Logger LOG = LoggerFactory.getLogger(LoginGrpcResource.class);

    private final UserService userService;
    private final AuthConfig authConfig;
    private final Key signingKey;
    private final PasswordEncoder passwordEncoder;

    public LoginGrpcResource(UserService userService,
                             AuthConfig authConfig,
                             Key signingKey,
                             PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authConfig = authConfig;
        this.signingKey = signingKey;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void login(LoginGrpcRequestDTO request,
                      StreamObserver<LoginGrpcResponseDTO> responseObserver) {
        try {
            authenticate(request)
                    .map(authUser ->
                            LoginGrpcResponseDTO.newBuilder()
                                    .setToken(JwtTokenUtil.generateToken(authUser, authConfig.tokenDuration(), signingKey))
                                    .setId(authUser.id().toString())
                                    .build())
                    .doOnNext(responseObserver::onNext)
                    .onErrorResume(Mono::error)
                    .block();

            responseObserver.onCompleted();
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
            responseObserver.onError(e);
        }
    }

    private Mono<User> authenticate(LoginGrpcRequestDTO request) {
        return userService.findByUsername(request.getUserName())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()) &&
                        request.getUserName().equals(user.getUsername()))
                .map(User.class::cast)
                .switchIfEmpty(Mono.error(new ApplicationAuthenticationException("Invalid User")));
    }
}
