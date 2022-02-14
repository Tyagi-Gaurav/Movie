package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.test.config.ApiGatewayConfig;
import com.gt.scr.movie.test.config.UserAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TestHealthCheckResource extends AbstractResource {

    @Autowired
    private ApiGatewayConfig apiGatewayConfig;

    @Autowired
    private UserAppConfig userAppConfig;

    @Autowired
    private ResponseHolder responseHolder;

    public void invokeStatus() {
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.contentUploadContextPath(),
                "/api/status", apiGatewayConfig.port());
        responseHolder.setResponse(this.get(fullUrl, new HttpEntity(HttpHeaders.EMPTY), String.class));
    }

    public void invokeMovieHealthcheck() {
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.contentUploadContextPath(),
                "/actuator/healthcheck", apiGatewayConfig.port());
        responseHolder.setResponse(this.get(fullUrl, new HttpEntity(HttpHeaders.EMPTY), String.class));
    }

    public void invokeUserHealthcheck() {
        String fullUrl = getFullUrl(userAppConfig.host().trim(),
                apiGatewayConfig.contentUploadContextPath(),
                "/actuator/healthcheck", userAppConfig.port());
        responseHolder.setResponse(this.get(fullUrl, new HttpEntity(HttpHeaders.EMPTY), String.class));
    }
}