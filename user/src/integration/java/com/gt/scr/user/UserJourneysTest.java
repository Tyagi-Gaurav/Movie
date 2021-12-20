package com.gt.scr.user;

import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.resource.domain.LoginRequestDTO;
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
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
@AutoConfigureWebFlux
@ActiveProfiles("UserJourneysTest")
public class UserJourneysTest {
    private ScenarioExecutor scenarioExecutor;
    private WebTestClient webTestClient;

    @Autowired
    private DataSource dataSource;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + serverPort + "/api";
        webTestClient = WebTestClient.bindToServer().baseUrl(baseUrl).build();
        scenarioExecutor = new ScenarioExecutor(webTestClient, dataSource);
    }

    @Test
    void createUserTest() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO)
                .then().expectReturnCode(204);
    }

    @Test
    void createUserTestWithInvalidData() {
        AccountCreateRequestDTO invalidAccountCreateRequest =
                TestObjectBuilder.invalidUserAccountCreateRequestDTO();
        scenarioExecutor
                .when().userIsCreatedFor(invalidAccountCreateRequest)
                .then().expectReturnCode(400);
    }

    @Test
    void createUserAndLoginTest() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO loginRequestDTO = TestObjectBuilder.loginRequestUsing(accountCreateRequestDTO);
        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .userLoginsWith(loginRequestDTO).expectReturnCode(200);
    }

    @Test
    void createUserAndLoginWithInvalidPasswordTest() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO loginRequestDTO = TestObjectBuilder.invalidPasswordLoginRequestUsing(accountCreateRequestDTO);
        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO)
                .expectReturnCode(204)
                .userLoginsWith(loginRequestDTO).expectReturnCode(401);
    }

    @Test
    void aNormalUserShouldNotBeAbleToAccessUserManagementEndpoints() {
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

    @Test
    void beAbleToFetchUserDetailsForAGivenUserByName() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .retrieveUserByName(accountCreateRequestDTO.userName())
                .then().retrievedUsersFirstNameIs(accountCreateRequestDTO.firstName())
                .expectReturnCode(200);
    }

    @Test
    void beAbleToFetchUserDetailsForAGivenUserById() {
        scenarioExecutor
                .when().retrieveUserById(UUID.fromString("dcba7802-2eae-42b2-818e-a27f8f380088"))
                .then().retrievedUsersFirstNameIs("Gaurav")
                .expectReturnCode(200);
    }

    @Test
    void adminShouldBeAbleToDeleteUser() {
        AccountCreateRequestDTO accountCreateRequestDTO = TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO loginRequestDTO = TestObjectBuilder.loginRequestUsing(accountCreateRequestDTO);
        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .then().userLoginsWith(loginRequestDTO).expectReturnCode(200)
                .then().deleteUser()
                .expectReturnCode(200)
                .then().userShouldNotExistInDatabase();
    }

    @Test
    void regularUserShouldNotBeAbleToCreateDuplicateAccount() {
        AccountCreateRequestDTO accountCreateRequestDTO = TestObjectBuilder.userAccountCreateRequestDTO();
        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .then().userIsCreatedFor(accountCreateRequestDTO)
                .then()
                .expectReturnCode(403);
    }
}
