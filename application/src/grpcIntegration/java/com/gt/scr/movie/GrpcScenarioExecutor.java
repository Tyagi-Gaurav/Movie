package com.gt.scr.movie;

import com.gt.scr.movie.grpc.AccountCreateGrpcRequestDTO;
import com.gt.scr.movie.grpc.CreateAccountServiceGrpc;
import com.gt.scr.movie.grpc.Empty;
import com.gt.scr.movie.grpc.LoginGrpcRequestDTO;
import com.gt.scr.movie.grpc.LoginGrpcResponseDTO;
import com.gt.scr.movie.grpc.LoginServiceGrpc;
import com.gt.scr.movie.grpc.MovieGrpcCreateRequestDTO;
import com.gt.scr.movie.grpc.MovieServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import static org.assertj.core.api.Assertions.assertThat;

public class GrpcScenarioExecutor {
    private final CreateAccountServiceGrpc.CreateAccountServiceBlockingStub accountServiceBlockingStub;
    private final LoginServiceGrpc.LoginServiceBlockingStub loginServiceBlockingStub;
    private final ManagedChannel managedChannel;
    private MovieServiceGrpc.MovieServiceBlockingStub movieServiceBlockingStub;
    private Object lastResponse;
    private String normalUserToken;
    private Exception lastException;

    public GrpcScenarioExecutor(ManagedChannel managedChannel) {
        accountServiceBlockingStub = CreateAccountServiceGrpc.newBlockingStub(managedChannel);
        loginServiceBlockingStub = LoginServiceGrpc.newBlockingStub(managedChannel);
        this.managedChannel = managedChannel;
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

    public GrpcScenarioExecutor loginResponseShouldHaveCorrectDetails() {
        LoginGrpcResponseDTO loginGrpcResponseDTO = (LoginGrpcResponseDTO) this.lastResponse;
        assertThat(loginGrpcResponseDTO.getToken()).isNotEmpty();
        assertThat(loginGrpcResponseDTO.getId()).isNotEmpty();
        this.normalUserToken = loginGrpcResponseDTO.getToken();
        return this;
    }

    public GrpcScenarioExecutor createMovieWith(MovieGrpcCreateRequestDTO movieGrpcCreateRequestDTO) {
        movieServiceBlockingStub = MovieServiceGrpc.newBlockingStub(managedChannel)
                .withCallCredentials(new IntegrationAuthenticationCallCredentials(normalUserToken));
        try {
            lastResponse = movieServiceBlockingStub.createMovie(movieGrpcCreateRequestDTO);
        } catch(Exception e) {
            lastException = e;
        }
        return this;
    }

    public GrpcScenarioExecutor errorIsReturnedWithStatus(Status status) {
        assertThat(lastException).isInstanceOf(StatusRuntimeException.class);
        var statusException = (StatusRuntimeException)lastException;
        assertThat(statusException.getStatus().getCode()).isEqualTo(status.getCode());
        return this;
    }
}
