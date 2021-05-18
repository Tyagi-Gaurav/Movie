package com.toptal.scr.tz.test.resource;

import com.toptal.scr.tz.test.config.TimeZoneAppConfig;
import com.toptal.scr.tz.test.domain.TestAccountCreateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TestUserManagementResource extends AbstractResource {

    @Autowired
    private TimeZoneAppConfig timeZoneAppConfig;

    @Autowired
    private ResponseHolder responseHolder;

    public void create(TestAccountCreateRequestDTO testAccountCreateRequestDTO) {
        String fullUrl = getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/manage", timeZoneAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.user.add.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestAccountCreateRequestDTO> requestObject = new HttpEntity<>(testAccountCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, requestObject, String.class));
    }

    public void delete(String regularUserId) {
        String fullUrl = String.format("%s?userId=%s", getFullUrl(timeZoneAppConfig.host().trim(),
                "/api/user/manage", timeZoneAppConfig.port()), regularUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.user.delete.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestAccountCreateRequestDTO> requestObject = new HttpEntity<>(headers);
        responseHolder.setResponse(this.delete(fullUrl, requestObject, String.class));
    }

//    public void readTimezones() {
//        String fullUrl = getFullUrl(timeZoneAppConfig.host().trim(),
//                "/api/user/timezone", timeZoneAppConfig.port());
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.timezone.read.v1+json");
//        headers.setBearerAuth(responseHolder.getToken());
//        HttpEntity<TestTimezoneCreateRequestDTO> requestObject = new HttpEntity<>(headers);
//        responseHolder.setResponse(this.get(fullUrl, requestObject, String.class));
//    }
//
//    public void deleteTimezone(UUID uuid) {
//        String fullUrl = String.format("%s?id=%s", getFullUrl(timeZoneAppConfig.host().trim(),
//                "/api/user/timezone", timeZoneAppConfig.port()), uuid.toString());
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.timezone.delete.v1+json");
//        headers.setBearerAuth(responseHolder.getToken());
//        HttpEntity<TestTimezoneCreateRequestDTO> requestObject = new HttpEntity<>(headers);
//
//        responseHolder.setResponse(this.delete(fullUrl, requestObject, String.class));
//    }
//
//    public void updateTimezone(TestTimezoneUpdateRequestDTO updateRequestDTO) {
//        String fullUrl = getFullUrl(timeZoneAppConfig.host().trim(),
//                "/api/user/timezone", timeZoneAppConfig.port());
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.timezone.update.v1+json");
//        headers.setBearerAuth(responseHolder.getToken());
//        HttpEntity<TestTimezoneUpdateRequestDTO> requestObject = new HttpEntity<>(updateRequestDTO, headers);
//        responseHolder.setResponse(this.put(fullUrl, requestObject, String.class));
//    }
}