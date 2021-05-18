package com.toptal.scr.tz.test.resource;

import com.toptal.scr.tz.test.config.TimeZoneAppConfig;
import com.toptal.scr.tz.test.domain.TestLoginRequestDTO;
import com.toptal.scr.tz.test.domain.TestLoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TestLoginResource extends AbstractResource {

    @Autowired
    private TimeZoneAppConfig timeZoneAppConfig;

    @Autowired
    private ResponseHolder responseHolder;

    public void create(TestLoginRequestDTO testLoginRequestDTO) {
        String fullUrl = getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/login", timeZoneAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.login.v1+json");
        HttpEntity<TestLoginRequestDTO> request = new HttpEntity<>(testLoginRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, request, String.class));

        if (responseHolder.getResponseCode() == 200) {
            TestLoginResponseDTO testLoginResponseDTO = responseHolder.readResponse(TestLoginResponseDTO.class);
            responseHolder.storeUserToken(testLoginResponseDTO.token());
            responseHolder.storeUserId(testLoginResponseDTO.id());
        }
    }
}