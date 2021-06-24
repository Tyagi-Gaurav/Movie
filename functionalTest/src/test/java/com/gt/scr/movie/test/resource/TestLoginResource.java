package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.test.config.TimeZoneAppConfig;
import com.gt.scr.movie.test.domain.TestLoginRequestDTO;
import com.gt.scr.movie.test.domain.TestLoginResponseDTO;
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