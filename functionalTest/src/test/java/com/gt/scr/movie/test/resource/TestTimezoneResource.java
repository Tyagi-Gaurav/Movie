package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.test.config.TimeZoneAppConfig;
import com.gt.scr.movie.test.domain.TestTimezoneCreateRequestDTO;
import com.gt.scr.movie.test.domain.TestTimezoneUpdateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestTimezoneCreateRequestDTO> requestObject = new HttpEntity<>(timezoneCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, requestObject, String.class));
    }

    public void createWithoutToken(TestTimezoneCreateRequestDTO testTimezoneCreateRequestDTO) {
        String fullUrl = getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/timezone", timeZoneAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.timezone.add.v1+json");
        HttpEntity<TestTimezoneCreateRequestDTO> requestObject = new HttpEntity<>(testTimezoneCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, requestObject, String.class));
    }

    public void readTimezones() {
        String fullUrl = getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/timezone", timeZoneAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.timezone.read.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestTimezoneCreateRequestDTO> requestObject = new HttpEntity<>(headers);
        responseHolder.setResponse(this.get(fullUrl, requestObject, String.class));
    }

    public void deleteTimezone(UUID uuid) {
        String fullUrl = String.format("%s?id=%s", getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/timezone", timeZoneAppConfig.port()), uuid.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.timezone.delete.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestTimezoneCreateRequestDTO> requestObject = new HttpEntity<>(headers);

        responseHolder.setResponse(this.delete(fullUrl, requestObject, String.class));
    }

    public void updateTimezone(TestTimezoneUpdateRequestDTO updateRequestDTO) {
        String fullUrl = getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/timezone", timeZoneAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.timezone.update.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestTimezoneUpdateRequestDTO> requestObject = new HttpEntity<>(updateRequestDTO, headers);
        responseHolder.setResponse(this.put(fullUrl, requestObject, String.class));
    }

    public void readTimezones(String userId) {
        String fullUrl = String.format("%s?userId=%s", getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/timezone", timeZoneAppConfig.port()), userId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.timezone.read.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestTimezoneCreateRequestDTO> requestObject = new HttpEntity<>(headers);
        responseHolder.setResponse(this.get(fullUrl, requestObject, String.class));
    }

    public void deleteTimezone(UUID timezoneId, String regularUserId) {
        String fullUrl = String.format("%s?id=%s&userId=%s", getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/timezone", timeZoneAppConfig.port()), timezoneId.toString(), regularUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.timezone.delete.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestTimezoneCreateRequestDTO> requestObject = new HttpEntity<>(headers);

        responseHolder.setResponse(this.delete(fullUrl, requestObject, String.class));
    }

    public void create(TestTimezoneCreateRequestDTO timezoneCreateRequestDTO, String regularUserId) {
        String fullUrl = String.format("%s?userId=%s", getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/timezone", timeZoneAppConfig.port()), regularUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.timezone.add.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestTimezoneCreateRequestDTO> requestObject = new HttpEntity<>(timezoneCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, requestObject, String.class));
    }

    public void updateTimezone(TestTimezoneUpdateRequestDTO updateRequestDTO, String regularUserId) {
        String fullUrl = String.format("%s?userId=%s", getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/timezone", timeZoneAppConfig.port()), regularUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.timezone.update.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestTimezoneUpdateRequestDTO> requestObject = new HttpEntity<>(updateRequestDTO, headers);
        responseHolder.setResponse(this.put(fullUrl, requestObject, String.class));
    }
}