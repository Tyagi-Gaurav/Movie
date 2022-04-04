package com.gt.scr.movie.filter;

import com.gt.scr.movie.config.AccessibleEndpointConfig;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class AccessibleEndpointFilter implements WebFilter {
    private final AccessibleEndpointConfig accessibleEndpointConfig;

    public AccessibleEndpointFilter(AccessibleEndpointConfig accessibleEndpointConfig) {
        this.accessibleEndpointConfig = accessibleEndpointConfig;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethodValue();

        if (!(accessibleEndpointConfig.isEnabled(method, path)
                || accessibleEndpointConfig.satisfiesRegex(method, path))) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            return Mono.empty();
        }

        return chain.filter(exchange);
    }
}
