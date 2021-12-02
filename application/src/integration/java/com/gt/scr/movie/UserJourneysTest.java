package com.gt.scr.movie;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
@AutoConfigureWebFlux
@ActiveProfiles("UserJourneysTest")
public class UserJourneysTest {
    private ScenarioExecutor scenarioExecutor;
    private WebTestClient webTestClient;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + serverPort + "/api";
        webTestClient = WebTestClient.bindToServer().baseUrl(baseUrl).build();
        scenarioExecutor = new ScenarioExecutor(webTestClient, dataSource);
    }

    @Test
    public void createUserTest() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO)
                .then().expectReturnCode(204);
    }

    @Test
    public void createUserTestWithInvalidData() {
        AccountCreateRequestDTO invalidAccountCreateRequest =
                TestObjectBuilder.invalidUserAccountCreateRequestDTO();
        scenarioExecutor
                .when().userIsCreatedFor(invalidAccountCreateRequest)
                .then().expectReturnCode(400);
    }

    @Test
    public void createUserAndLoginTest() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO loginRequestDTO = TestObjectBuilder.loginRequestUsing(accountCreateRequestDTO);
        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .userLoginsWith(loginRequestDTO).expectReturnCode(200);
    }

    @Test
    public void createUserAndLoginWithInvalidPasswordTest() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO loginRequestDTO = TestObjectBuilder.invalidPasswordLoginRequestUsing(accountCreateRequestDTO);
        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO)
                .expectReturnCode(204)
                .userLoginsWith(loginRequestDTO).expectReturnCode(401);
    }

    @Test
    public void aNormalUserShouldNotBeAbleToAccessUserManagementEndpoints() {
        AccountCreateRequestDTO accountCreateRequestDTO = TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO loginRequestDTO = TestObjectBuilder.loginRequestUsing(accountCreateRequestDTO);
        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .userLoginsWith(loginRequestDTO).expectReturnCode(200)
                .userRetrievesListOfAllUsers().expectReturnCode(403);
    }

    @Test
    void regularAccountCreateIsOnlyForNormalUsers() {
        AccountCreateRequestDTO adminAccountCreateRequestDTO = TestObjectBuilder.adminAccountCreateRequest();
        scenarioExecutor
                .when().userIsCreatedFor(adminAccountCreateRequestDTO)
                .then().expectReturnCode(403);
    }
}
