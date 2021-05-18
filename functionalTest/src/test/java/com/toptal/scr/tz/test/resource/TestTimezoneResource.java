package com.toptal.scr.tz.test.resource;

import com.toptal.scr.tz.test.config.TimeZoneAppConfig;
import com.toptal.scr.tz.test.domain.TestAccountCreateRequestDTO;
import com.toptal.scr.tz.test.domain.TestTimezoneCreateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TestTimezoneResource extends AbstractResource {

    @Autowired
    private TimeZoneAppConfig timeZoneAppConfig;

    @Autowired
    private ResponseHolder responseHolder;

    public void create(TestTimezoneCreateRequestDTO timezoneCreateRequestDTO) {
        String fullUrl = getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/timezone", timeZoneAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.timezone.add.v1+json");
        System.out.println(responseHolder.getToken());
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestTimezoneCreateRequestDTO> request = new HttpEntity<>(timezoneCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, request, String.class));
    }
}