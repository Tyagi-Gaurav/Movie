package com.gt.scr.movie;

import com.gt.scr.movie.grpc.AccountCreateGrpcRequestDTO;
import com.gt.scr.movie.grpc.LoginGrpcRequestDTO;
import com.gt.scr.movie.grpc.MovieGrpcCreateRequestDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.gt.scr.movie.TestBuilder.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class,
        properties = {
                "management.endpoint.health.enabled=true",
                "management.endpoints.web.exposure.include=*",
                "management.server.port=0",
                "management.port=0"
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
@EnableAutoConfiguration
@ActiveProfiles({"grpc"})
class GRPCIntegrationTest {
    private static ManagedChannel managedChannel;
    private static GrpcScenarioExecutor grpcScenarioExecutor;

    @BeforeAll
    static void setUp() {
        managedChannel = ManagedChannelBuilder.forTarget("localhost:8900")
                .usePlaintext().build();
        grpcScenarioExecutor = new GrpcScenarioExecutor(managedChannel);
    }

    @AfterAll
    static void afterAll() {
        managedChannel.shutdownNow();
    }

    @Test
    @DisplayName("Account Create and Login")
    public void runScenario1() {
        AccountCreateGrpcRequestDTO requestDTO = accountCreate();
        LoginGrpcRequestDTO loginGrpcRequestDTO = loginCreate();

        grpcScenarioExecutor
                .createUserWith(requestDTO)
                .and().resultIsReturned()
                .then()
                .loginUserWith(loginGrpcRequestDTO)
                .then()
                .loginResponseShouldHaveCorrectDetails();
    }

    @Test
    @DisplayName("Account Create, Login and Create Movie Entry")
    public void runScenario2() {
        AccountCreateGrpcRequestDTO requestDTO = accountCreate();
        LoginGrpcRequestDTO loginGrpcRequestDTO = loginCreate();
        MovieGrpcCreateRequestDTO movieGrpcCreateRequestDTO = movieCreate();

        grpcScenarioExecutor
                .createUserWith(requestDTO)
                .and().resultIsReturned()
                .then()
                .loginUserWith(loginGrpcRequestDTO)
                .then()
                .loginResponseShouldHaveCorrectDetails()
                .then()
                .createMovieWith(movieGrpcCreateRequestDTO)
                .and().resultIsReturned();
    }

    @Test
    @DisplayName("Account Create, Login and Create Movie Entry twice to return error")
    public void runScenario3() {
        AccountCreateGrpcRequestDTO requestDTO = accountCreate();
        LoginGrpcRequestDTO loginGrpcRequestDTO = loginCreate();
        MovieGrpcCreateRequestDTO movieGrpcCreateRequestDTO = movieCreate();

        grpcScenarioExecutor
                .createUserWith(requestDTO)
                .and().resultIsReturned()
                .then()
                .loginUserWith(loginGrpcRequestDTO)
                .then()
                .loginResponseShouldHaveCorrectDetails()
                .then()
                .createMovieWith(movieGrpcCreateRequestDTO)
                .then().resultIsReturned()
                .createMovieWith(movieGrpcCreateRequestDTO)
                .and().errorIsReturnedWithStatus(Status.ALREADY_EXISTS);
    }
}
