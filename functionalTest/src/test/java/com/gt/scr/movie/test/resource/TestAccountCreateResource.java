package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.test.config.MovieAppConfig;
import com.gt.scr.movie.test.domain.TestAccountCreateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TestAccountCreateResource extends AbstractResource {

    @Autowired
    private MovieAppConfig movieAppConfig;

    @Autowired
    private ResponseHolder responseHolder;

    public void create(TestAccountCreateRequestDTO accountCreateRequestDTO) {
        String fullUrl = getFullUrl(movieAppConfig.host().trim(),
                "/api/user/account/create", movieAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd+account.create.v1+json");
        HttpEntity<TestAccountCreateRequestDTO> request = new HttpEntity<>(accountCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, request, String.class));
    }
}