package com.gt.scr.movie;

import com.gt.scr.movie.grpc.AccountCreateGrpcRequestDTO;
import com.gt.scr.movie.grpc.LoginGrpcRequestDTO;
import com.gt.scr.movie.grpc.MovieGrpcCreateRequestDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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
        AccountCreateGrpcRequestDTO requestDTO = AccountCreateGrpcRequestDTO.newBuilder()
                .setFirstName("testFirstName")
                .setLastName("testLastName")
                .setPassword("testPassword")
                .setUserName("testUserName")
                .setRole("ADMIN").build();

        LoginGrpcRequestDTO loginGrpcRequestDTO = LoginGrpcRequestDTO.newBuilder()
                .setUserName("testUserName")
                .setPassword("testPassword")
                .build();

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
        AccountCreateGrpcRequestDTO requestDTO = AccountCreateGrpcRequestDTO.newBuilder()
                .setFirstName("testFirstName")
                .setLastName("testLastName")
                .setPassword("testPassword")
                .setUserName("testUserName")
                .setRole("ADMIN").build();

        LoginGrpcRequestDTO loginGrpcRequestDTO = LoginGrpcRequestDTO.newBuilder()
                .setUserName("testUserName")
                .setPassword("testPassword")
                .build();

        MovieGrpcCreateRequestDTO movieGrpcCreateRequestDTO = MovieGrpcCreateRequestDTO.newBuilder()
                .setName("TestMovieName")
                .setRating(10.0)
                .setYearProduced(2020)
                .build();

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
}
