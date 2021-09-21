package com.gt.scr.movie;

import com.gt.scr.movie.grpc.AccountCreateGrpcRequestDTO;
import com.gt.scr.movie.grpc.CreateAccountServiceGrpc;
import com.gt.scr.movie.grpc.LoginGrpcRequestDTO;
import com.gt.scr.movie.grpc.LoginGrpcResponseDTO;
import com.gt.scr.movie.grpc.LoginServiceGrpc;
import io.grpc.ManagedChannel;

import static org.assertj.core.api.Assertions.assertThat;

public class GrpcScenarioExecutor {
    private final CreateAccountServiceGrpc.CreateAccountServiceBlockingStub accountServiceBlockingStub;
    private final LoginServiceGrpc.LoginServiceBlockingStub loginServiceBlockingStub;
    private Object lastResponse;

    public GrpcScenarioExecutor(ManagedChannel managedChannel) {
        accountServiceBlockingStub = CreateAccountServiceGrpc.newBlockingStub(managedChannel);
        loginServiceBlockingStub = LoginServiceGrpc.newBlockingStub(managedChannel);
    }

    public GrpcScenarioExecutor createUserWith(AccountCreateGrpcRequestDTO requestDTO) {
        this.lastResponse = accountServiceBlockingStub.createAccount(requestDTO);
        return this;
    }

    public GrpcScenarioExecutor when() {
        return this;
    }

    public GrpcScenarioExecutor and() {
        return this;
    }

    public GrpcScenarioExecutor then() {
        return this;
    }

    public GrpcScenarioExecutor resultIsReturned() {
        assertThat(lastResponse).isNotNull();
        return this;
    }

    public GrpcScenarioExecutor loginUserWith(LoginGrpcRequestDTO loginGrpcRequestDTO) {
        lastResponse = loginServiceBlockingStub.login(loginGrpcRequestDTO);
        return this;
    }

    public void loginResponseShouldHaveCorrectDetails() {
        LoginGrpcResponseDTO loginGrpcResponseDTO = (LoginGrpcResponseDTO) this.lastResponse;
        assertThat(loginGrpcResponseDTO.getToken()).isNotEmpty();
        assertThat(loginGrpcResponseDTO.getId()).isNotEmpty();
    }
}
