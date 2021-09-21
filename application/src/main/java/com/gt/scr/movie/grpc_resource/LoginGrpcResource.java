package com.gt.scr.movie.grpc_resource;

import com.gt.scr.movie.config.AuthConfig;
import com.gt.scr.movie.exception.ApplicationAuthenticationException;
import com.gt.scr.movie.filter.JwtTokenUtil;
import com.gt.scr.movie.grpc.LoginGrpcRequestDTO;
import com.gt.scr.movie.grpc.LoginGrpcResponseDTO;
import com.gt.scr.movie.grpc.LoginServiceGrpc;
import com.gt.scr.movie.service.domain.User;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.security.Key;

public class LoginGrpcResource extends LoginServiceGrpc.LoginServiceImplBase {
    private static final Logger LOG = LoggerFactory.getLogger(LoginGrpcResource.class);

    private AuthenticationManager authenticationManager;
    private AuthConfig authConfig;
    private Key signingKey;

    public LoginGrpcResource(AuthenticationManager authenticationManager,
                             AuthConfig authConfig,
                             Key signingKey) {
        this.authenticationManager = authenticationManager;
        this.authConfig = authConfig;
        this.signingKey = signingKey;
    }

    @Override
    public void login(LoginGrpcRequestDTO request,
                      StreamObserver<LoginGrpcResponseDTO> responseObserver) {
        try {
            if (LOG.isInfoEnabled()) {
                LOG.info("Login user request");
            }
            var user = authenticate(request);
            final String token = JwtTokenUtil.generateToken(user, authConfig.tokenDuration(), signingKey);
            LoginGrpcResponseDTO response = LoginGrpcResponseDTO.newBuilder()
                    .setToken(token)
                    .setId(user.id().toString()).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
            responseObserver.onError(e);
        }
    }

    private User authenticate(LoginGrpcRequestDTO request) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
            return (User) auth.getPrincipal();
        } catch (AuthenticationException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
            throw new ApplicationAuthenticationException("Authentication failed", e);
        }
    }
}
