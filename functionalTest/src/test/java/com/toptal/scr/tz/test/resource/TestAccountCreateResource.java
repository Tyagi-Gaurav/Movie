package com.toptal.scr.tz.test.resource;

import com.toptal.scr.tz.test.config.TimeZoneAppConfig;
import com.toptal.scr.tz.test.domain.TestAccountCreateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TestAccountCreateResource extends AbstractResource {

    @Autowired
    private TimeZoneAppConfig timeZoneAppConfig;

    @Autowired
    private ResponseHolder responseHolder;

    public void create(TestAccountCreateRequestDTO accountCreateRequestDTO) {
        String fullUrl = getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/account/create", timeZoneAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd+account.create.v1+json");
        HttpEntity<TestAccountCreateRequestDTO> request = new HttpEntity<>(accountCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, request, String.class));
    }
}