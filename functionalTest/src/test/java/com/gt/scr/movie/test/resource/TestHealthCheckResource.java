package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.test.config.MovieAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TestHealthCheckResource extends AbstractResource {

    @Autowired
    private MovieAppConfig movieAppConfig;

    @Autowired
    private ResponseHolder responseHolder;

    public void invokeStatus() {
        String fullUrl = getFullUrl(movieAppConfig.host().trim(),
                "/api/status", movieAppConfig.port());
        responseHolder.setResponse(this.get(fullUrl, new HttpEntity(HttpHeaders.EMPTY), String.class));
    }
}