package com.gt.scr.movie;

import com.gt.scr.movie.grpc.AccountCreateGrpcRequestDTO;
import com.gt.scr.movie.grpc.CreateAccountServiceGrpc;
import com.gt.scr.movie.grpc.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

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

    @BeforeAll
    static void setUp() {
        managedChannel = ManagedChannelBuilder.forTarget("localhost:8900")
                .usePlaintext().build();
    }

    @AfterAll
    static void afterAll() {
        managedChannel.shutdownNow();
    }

    @Test
    public void runThis() {
        CreateAccountServiceGrpc.CreateAccountServiceBlockingStub stub =
                CreateAccountServiceGrpc.newBlockingStub(managedChannel);

        AccountCreateGrpcRequestDTO requestDTO = AccountCreateGrpcRequestDTO.newBuilder()
                .setFirstName("testFirstName")
                .setLastName("testLastName")
                .setPassword("testPassword")
                .setUserName("testUserName")
                .setRole("ADMIN").build();

        Empty response = stub.createAccount(requestDTO);
        assertThat(response).isNotNull();
    }
}
