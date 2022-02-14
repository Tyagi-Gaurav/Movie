package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.test.config.ApiGatewayConfig;
import com.gt.scr.movie.test.domain.TestAccountCreateRequestDTO;
import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TestAccountCreateResource extends AbstractResource {

    @Autowired
    private ApiGatewayConfig apiGatewayConfig;

    @Autowired
    private ResponseHolder responseHolder;

    @Autowired
    private ManagedChannel managedChannel;

    public void create(TestAccountCreateRequestDTO accountCreateRequestDTO) {
        createUsingRest(accountCreateRequestDTO);
    }

    private void createUsingRest(TestAccountCreateRequestDTO accountCreateRequestDTO) {
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.userContextPath(), "/account/create", apiGatewayConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd+account.create.v1+json");
        HttpEntity<TestAccountCreateRequestDTO> request = new HttpEntity<>(accountCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, request, String.class));
    }
}