package com.gt.scr.movie.config;

import com.gt.scr.movie.grpc_resource.AuthorizationInterceptor;
import com.gt.scr.movie.grpc_resource.CreateAccountGrpcResource;
import com.gt.scr.movie.grpc_resource.ExceptionHandlerInterceptor;
import com.gt.scr.movie.grpc_resource.LoginGrpcResource;
import com.gt.scr.movie.grpc_resource.MovieGrpcResource;
import com.gt.scr.movie.service.MovieService;
import com.gt.scr.movie.service.UserService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
    public LoginGrpcResource loginGrpcResource(UserService userService,
                                               AuthConfig authConfig,
                                               Key signingKey,
                                               PasswordEncoder passwordEncoder) {
        return new LoginGrpcResource(userService, authConfig, signingKey, passwordEncoder);
    }

    @Bean
    public AuthorizationInterceptor authorizationInterceptor(Key signingKey,
                                                             UserService userService) {
        return new AuthorizationInterceptor(signingKey, userService);
    }

    @Bean
    public MovieGrpcResource movieGrpcResource(MovieService movieService) {
        return new MovieGrpcResource(movieService);
    }

    @Bean
    public ExceptionHandlerInterceptor exceptionHandlerInterceptor() {
        return new ExceptionHandlerInterceptor();
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Server server(CreateAccountGrpcResource createAccountGrpcResource,
                         LoginGrpcResource loginGrpcResource,
                         MovieGrpcResource movieGrpcResource,
                         AuthorizationInterceptor authorizationInterceptor,
                         ExceptionHandlerInterceptor exceptionHandlerInterceptor) {
        return ServerBuilder.forPort(9900)
                .addService(createAccountGrpcResource)
                .addService(loginGrpcResource)
                .addService(ServerInterceptors.intercept(movieGrpcResource, authorizationInterceptor))
                .intercept(exceptionHandlerInterceptor)
                .build();
    }
}
