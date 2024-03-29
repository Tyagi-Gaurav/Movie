package com.gt.scr.movie.security;

import com.gt.scr.movie.config.AuthenticationManager;
import com.gt.scr.movie.config.SecurityContextRepository;
import com.gt.scr.movie.service.FetchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.Key;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Autowired
    private FetchUserService userDetailsService;

    @Bean
    public UserDetailsRepositoryReactiveAuthenticationManager userDetailsRepositoryReactiveAuthenticationManager() {
        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity httpSecurity,
                                                JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                                @Qualifier("signingKey") Key signingKey) {
        return httpSecurity
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/swagger-ui**", "/webjars/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .pathMatchers("/status", "/actuator/**").permitAll()
                .pathMatchers("/user/{userId}/movie").hasAuthority("ADMIN")
                .anyExchange().authenticated().and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .securityContextRepository(new SecurityContextRepository(
                        new AuthenticationManager(userDetailsService, signingKey)
                ))
                .build();
    }

}