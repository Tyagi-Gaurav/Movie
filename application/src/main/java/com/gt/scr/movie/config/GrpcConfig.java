package com.gt.scr.movie.config;

import com.gt.scr.movie.grpc_resource.CreateAccountGrpcResource;
import com.gt.scr.movie.grpc_resource.LoginGrpcResource;
import com.gt.scr.movie.service.UserService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;

@Configuration
@Profile({"prod", "grpc"})
public class GrpcConfig {
    @Bean
    public CreateAccountGrpcResource createAccountGrpcResource(UserService userService,
                                                               PasswordEncoder passwordEncoder) {
        return new CreateAccountGrpcResource(userService, passwordEncoder);
    }

    @Bean
    public LoginGrpcResource loginGrpcResource(AuthenticationManager authenticationManager,
                                               AuthConfig authConfig,
                                               Key signingKey) {
        return new LoginGrpcResource(authenticationManager, authConfig, signingKey);
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Server server(CreateAccountGrpcResource createAccountGrpcResource,
                         LoginGrpcResource loginGrpcResource) {
        return ServerBuilder.forPort(8900)
                .addService(createAccountGrpcResource)
                .addService(loginGrpcResource)
                .build();
    }
}
