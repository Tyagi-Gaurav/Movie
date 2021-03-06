package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.test.config.ApiGatewayConfig;
import com.gt.scr.movie.test.domain.TestByteStreamUploadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TestMovieContentUploadResource extends AbstractResource {

    @Autowired
    private ApiGatewayConfig apiGatewayConfig;

    @Autowired
    private ResponseHolder responseHolder;

    public void uploadContentFor(TestByteStreamUploadDTO testMovieContentUploadRequestDTO) {
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.contentUploadContextPath(),
                "/api/user/movie/stream", apiGatewayConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.stream.add.v1+json");
        headers.set(HttpHeaders.ACCEPT, "application/vnd.movie.stream.add.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestByteStreamUploadDTO> requestObject = new HttpEntity<>(testMovieContentUploadRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, requestObject, String.class));
    }
}