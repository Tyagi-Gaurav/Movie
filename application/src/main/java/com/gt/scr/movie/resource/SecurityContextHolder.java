package com.gt.scr.movie.resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class SecurityContextHolder {
    private final Mono<SecurityContext> securityContextMono = ReactiveSecurityContextHolder.getContext();

    public <T> Mono<T> getContext(Class<T> clazz) {
        return securityContextMono
                .filter(ctx -> Objects.nonNull(ctx.getAuthentication()))
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new IllegalAccessException("UserProfile not found"))))
                .map(clazz::cast);
    }
}
