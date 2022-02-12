package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.grpc.LoginGrpcRequestDTO;
import com.gt.scr.movie.grpc.LoginGrpcResponseDTO;
import com.gt.scr.movie.grpc.LoginServiceGrpc;
import com.gt.scr.movie.test.config.MovieAppConfig;
import com.gt.scr.movie.test.config.TestEnvironment;
import com.gt.scr.movie.test.domain.TestLoginRequestDTO;
import com.gt.scr.movie.test.domain.TestLoginResponseDTO;
import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TestLoginResource extends AbstractResource {

    @Autowired
    private MovieAppConfig movieAppConfig;

    @Autowired
    private ResponseHolder responseHolder;

    @Autowired
    private ManagedChannel managedChannel;

    public void doLogin(TestLoginRequestDTO testLoginRequestDTO) {
        if (TestEnvironment.isGrpc()) {
            loginUsingGrpc(testLoginRequestDTO);
        } else {
            loginUsingRest(testLoginRequestDTO);
        }
    }

    public void loginUsingRest(TestLoginRequestDTO testLoginRequestDTO) {
        String fullUrl = getFullUrl(movieAppConfig.host().trim(),
                movieAppConfig.contextPath(),
                "/api/user/login", movieAppConfig.port());
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

    public void loginUsingGrpc(TestLoginRequestDTO testLoginRequestDTO) {
        LoginGrpcRequestDTO loginGrpcRequestDTO = LoginGrpcRequestDTO.newBuilder()
                .setPassword(testLoginRequestDTO.password())
                .setUserName(testLoginRequestDTO.userName())
                .build();

        LoginServiceGrpc.LoginServiceBlockingStub loginServiceBlockingStub =
                LoginServiceGrpc.newBlockingStub(managedChannel);

        LoginGrpcResponseDTO responseDTO = loginServiceBlockingStub.login(loginGrpcRequestDTO);

        if (responseDTO != null) {
            responseHolder.setResponse(ResponseEntity.status(HttpStatus.OK)
                    .body(new TestLoginResponseDTO(responseDTO.getToken(), responseDTO.getId())));
            responseHolder.storeUserToken(responseDTO.getToken());
            responseHolder.storeUserId(responseDTO.getId());
        } else {
            responseHolder.setResponse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }
}