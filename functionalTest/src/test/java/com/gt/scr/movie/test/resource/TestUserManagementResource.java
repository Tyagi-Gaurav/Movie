package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.test.config.ApiGatewayConfig;
import com.gt.scr.movie.test.domain.TestAccountCreateRequestDTO;
import com.gt.scr.movie.test.domain.TestEmptyRequestDTO;
import com.gt.scr.movie.test.domain.TestUserListResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TestUserManagementResource extends AbstractResource {

    @Autowired
    private ApiGatewayConfig apiGatewayConfig;

    @Autowired
    private ResponseHolder responseHolder;

    public void create(TestAccountCreateRequestDTO testAccountCreateRequestDTO) {
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.userContextPath(),
                "/manage", apiGatewayConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.user.add.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestAccountCreateRequestDTO> requestObject = new HttpEntity<>(testAccountCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, requestObject, String.class));
    }

    public void delete(String regularUserId) {
        String fullUrl = String.format("%s?userId=%s", getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.userContextPath(),
                "/manage", apiGatewayConfig.port()), regularUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.user.delete.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestEmptyRequestDTO> requestObject = new HttpEntity<>(headers);
        responseHolder.setResponse(this.delete(fullUrl, requestObject, String.class));
    }

    public void getAllUsers() {
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.userContextPath(),
                "/manage", apiGatewayConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.user.read.v1+json");
        headers.set(HttpHeaders.ACCEPT, "application/vnd.user.read.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestEmptyRequestDTO> requestObject = new HttpEntity<>(headers);
        responseHolder.setResponse(this.get(fullUrl, requestObject, TestUserListResponseDTO.class));
    }
}