package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.test.config.ApiGatewayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TestMetricsResource extends AbstractResource {

    @Autowired
    private ApiGatewayConfig apiGatewayConfig;

    @Autowired
    private ResponseHolder responseHolder;

    public void invokeMovieMetrics() {
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.contentUploadContextPath(),
                "/actuator/metrics", apiGatewayConfig.port());
        responseHolder.setResponse(this.get(fullUrl, new HttpEntity(HttpHeaders.EMPTY), String.class));
    }
}