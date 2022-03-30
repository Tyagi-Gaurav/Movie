package com.gt.scr.movie.filter;

import com.gt.scr.movie.config.ToggleConfig;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class ToggleEndpointFilter implements WebFilter {
    private final ToggleConfig toggleConfig;

    public ToggleEndpointFilter(ToggleConfig toggleConfig) {
        this.toggleConfig = toggleConfig;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethodValue();
        if (toggleConfig.contains(method, path)) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            return Mono.empty();
        }

        return chain.filter(exchange);
    }
}
