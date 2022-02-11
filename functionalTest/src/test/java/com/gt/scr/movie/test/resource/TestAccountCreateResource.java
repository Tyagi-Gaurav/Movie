package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.grpc.AccountCreateGrpcRequestDTO;
import com.gt.scr.movie.grpc.CreateAccountServiceGrpc;
import com.gt.scr.movie.grpc.Empty;
import com.gt.scr.movie.test.config.TestEnvironment;
import com.gt.scr.movie.test.config.MovieAppConfig;
import com.gt.scr.movie.test.domain.TestAccountCreateRequestDTO;
import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TestAccountCreateResource extends AbstractResource {

    @Autowired
    private MovieAppConfig movieAppConfig;

    @Autowired
    private ResponseHolder responseHolder;

    @Autowired
    private ManagedChannel managedChannel;

    public void create(TestAccountCreateRequestDTO accountCreateRequestDTO) {
        if (TestEnvironment.isGrpc()) {
            createUsingGrpc(accountCreateRequestDTO);
        } else {
            createUsingRest(accountCreateRequestDTO);
        }
    }

    private void createUsingRest(TestAccountCreateRequestDTO accountCreateRequestDTO) {
        String fullUrl = getFullUrl(movieAppConfig.host().trim(),
                "/content/api/user/account/create", movieAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd+account.create.v1+json");
        HttpEntity<TestAccountCreateRequestDTO> request = new HttpEntity<>(accountCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, request, String.class));
    }

    public void createUsingGrpc(TestAccountCreateRequestDTO accountCreateRequestDTO) {
        CreateAccountServiceGrpc.CreateAccountServiceBlockingStub stub =
                CreateAccountServiceGrpc.newBlockingStub(managedChannel);

        AccountCreateGrpcRequestDTO requestDTO = AccountCreateGrpcRequestDTO.newBuilder()
                .setFirstName(accountCreateRequestDTO.firstName())
                .setLastName(accountCreateRequestDTO.lastName())
                .setPassword(accountCreateRequestDTO.password())
                .setUserName(accountCreateRequestDTO.userName())
                .setRole(accountCreateRequestDTO.role()).build();

        Empty response = stub.createAccount(requestDTO);
        if (response != null) {
            responseHolder.setResponse(ResponseEntity.status(204).build());
        } else {
            responseHolder.setResponse(ResponseEntity.status(500).build());
        }
    }
}