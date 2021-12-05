package com.gt.scr.user.filter;

import com.gt.scr.metrics.EndpointHistogram;
import com.gt.scr.metrics.EndpointRequestCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Instant;

public class MetricsInterceptor implements WebFilter {

    private static final Logger LOG = LoggerFactory.getLogger(MetricsInterceptor.class);

    private final EndpointRequestCounter endpointRequestCounter;
    private final EndpointHistogram endpointHistogram;

    public MetricsInterceptor(EndpointRequestCounter endpointRequestCounter,
                              EndpointHistogram endpointHistogram) {
        this.endpointRequestCounter = endpointRequestCounter;
        this.endpointHistogram = endpointHistogram;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        HttpMethod method = request.getMethod();
        String path = request.getURI().getPath();
        endpointRequestCounter.increment(String.valueOf(method), path);
        Instant startTime = Instant.now();

        Mono<Void> result = chain.filter(exchange);

        long duration = Instant.now().toEpochMilli() - startTime.toEpochMilli();

        LOG.info("Duration of request was : {} milli seconds", duration);
        endpointHistogram.observe(duration);

        return result;
    }
}
