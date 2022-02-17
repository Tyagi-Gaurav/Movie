package com.gt.scr.user;

import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.resource.domain.LoginRequestDTO;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.opentable.db.postgres.junit5.EmbeddedPostgresExtension;
import com.opentable.db.postgres.junit5.SingleInstancePostgresExtension;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ExtendWith(VertxExtension.class)
public class UserJourneysTest {
    @RegisterExtension
    public final static SingleInstancePostgresExtension singleInstancePostgresExtension = EmbeddedPostgresExtension.singleInstance();

    private ScenarioExecutor scenarioExecutor;

    @BeforeEach
    void setUp(Vertx vertx, VertxTestContext vertxTestContext) {
        EmbeddedPostgres embeddedPostgres = singleInstancePostgresExtension.getEmbeddedPostgres();
        JsonObject config = new JsonObject()
                .put("db.port", embeddedPostgres.getPort())
                .put("db.name", "postgres")
                .put("db.host", "localhost")
                .put("db.user", embeddedPostgres.getUserName())
                .put("db.password", embeddedPostgres.getPassword())
                .put("auth.token.key", "19CA249C582715657BDCAB1FB31E69F854443A4FE3CBAFFD215E3F3676")
                .put("http.port", 5050);

        String baseUrl = "http://localhost:" + 5050 + "/api/";
        WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl(baseUrl).build();
        scenarioExecutor = new ScenarioExecutor(webTestClient, embeddedPostgres.getPostgresDatabase());

        vertx.deployVerticle(new Application(), new DeploymentOptions()
                .setConfig(config), vertxTestContext.succeeding(event -> {
            scenarioExecutor.addAdminUser(embeddedPostgres.getPostgresDatabase());
            vertxTestContext.completeNow();
        }));
    }

    @Test
    @Timeout(value = 5, timeUnit = TimeUnit.SECONDS)
    void statusCheckTest() {
        scenarioExecutor
                .checkStatusOfApplication()
                .then().expectReturnCode(200)
                .then().expectBodyToBeEqualTo(
                        "{" +
                                "\"status\":\"UP\"," +
                                "\"checks\":" +
                                "[{" +
                                    "\"id\":\"status\"," +
                                    "\"status\":\"UP\"" +
                                "}]," +
                                "\"outcome\":\"UP" +
                                "\"}"
                );
    }

    @Test
    @Timeout(value = 5, timeUnit = TimeUnit.SECONDS)
    void createUserTest() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();

        scenarioExecutor
                .userIsCreatedFor(accountCreateRequestDTO)
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
    void createUserAndLogin() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();

        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .userLoginsWith(accountCreateRequestDTO).expectReturnCode(200);
    }

    @Test
    void requestIdShouldBeGeneratedWhenItsNotThere() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();

        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .userLoginsWith(accountCreateRequestDTO).expectReturnCode(200)
                .expectRequestIdIsReturnedInResponse();
    }

    @Test
    void requestIdShouldNotBeGeneratedWhenItsAlreadyThere() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        UUID requestId = UUID.randomUUID();

        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .userLoginsWithCustomRequestId(accountCreateRequestDTO, requestId).expectReturnCode(200)
                .expectRequestIdIsReturnedInResponseIs(requestId.toString());
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
    void createUserAndLoginWithInvalidUserTest() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO loginRequestDTO = TestObjectBuilder.invalidUserLoginRequestUsing(accountCreateRequestDTO);

        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO)
                .expectReturnCode(204)
                .userLoginsWith(loginRequestDTO).expectReturnCode(401);
    }

    @Test
    void adminUserShouldNotBeAllowedToCreateUsersViaNormalEndpoint() {
        AccountCreateRequestDTO accountCreateRequestDTO = TestObjectBuilder.adminAccountCreateRequest();

        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO)
                .expectReturnCode(403);
    }

    @Test
    void adminUserShouldBeAbleToCreateOtherUsers() {
        AccountCreateRequestDTO accountCreateRequestDTO = TestObjectBuilder.adminAccountCreateRequest();

        scenarioExecutor
                .when().globalAdminUserLogins().expectReturnCode(200)
                .adminUserCreatesARegularUserFor(accountCreateRequestDTO)
                .expectReturnCode(204);
    }

    @Test
    void aNormalUserShouldNotBeAbleToAccessUserManagementEndpoints() {
        AccountCreateRequestDTO accountCreateRequestDTO = TestObjectBuilder.userAccountCreateRequestDTO();

        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .userLoginsWith(accountCreateRequestDTO).expectReturnCode(200)
                .regularUserRetrievesListOfAllUsers().expectReturnCode(403);
    }

    @Test
    void adminUserShouldBeAbleToListAlTheUsersOfTheSystem() {
        scenarioExecutor
                .when().globalAdminUserLogins().expectReturnCode(200)
                .adminUserRetrievesListOfAllUsers().expectReturnCode(200);
    }

    @ParameterizedTest
    @ValueSource(strings = "token")
    @NullSource
    void anyUserShouldNotBeAbleToAccessWithAFakeToken(String token) {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();

        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .userLoginsWith(accountCreateRequestDTO).expectReturnCode(200)
                .retrieveUserByNameForRegularUserUsingToken(accountCreateRequestDTO.userName(),
                        token).expectReturnCode(400);
    }

    @Test
    void regularAccountCreateIsOnlyForNormalUsers() {
        AccountCreateRequestDTO adminAccountCreateRequestDTO = TestObjectBuilder.adminAccountCreateRequest();

        scenarioExecutor
                .when().userIsCreatedFor(adminAccountCreateRequestDTO)
                .then().expectReturnCode(403);
    }

    @Test
    void beAbleToFetchUserDetailsForAGivenUserByNameForRegularUser() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();

        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .userLoginsWith(accountCreateRequestDTO).expectReturnCode(200)
                .retrieveUserByNameForRegularUser(accountCreateRequestDTO.userName()).expectReturnCode(200)
                .then().retrievedUsersFirstNameIs(accountCreateRequestDTO.firstName())
                .expectReturnCode(200);
    }

    @Test
    void beAbleToFetchUserDetailsForAGivenUserByNameForAdminUser() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();

        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .when().globalAdminUserLogins().expectReturnCode(200)
                .retrieveUserByNameForAdminUser(accountCreateRequestDTO.userName()).expectReturnCode(200)
                .then().retrievedUsersFirstNameIs(accountCreateRequestDTO.firstName())
                .expectReturnCode(200);
    }

    @Test
    void beAbleToFetchUserDetailsForAGivenUserByIdByRegularUser() {
        scenarioExecutor
                .when().retrieveUserById(UUID.fromString("dcba7802-2eae-42b2-818e-a27f8f380088"))
                .expectReturnCode(200)
                .then().retrievedUsersFirstNameIs("Gaurav");
    }

    @Test
    void adminShouldBeAbleToDeleteUser() {
        AccountCreateRequestDTO accountCreateRequestDTO = TestObjectBuilder.userAccountCreateRequestDTO();

        scenarioExecutor
                .when().globalAdminUserLogins().expectReturnCode(200)
                .when().adminUserCreatesARegularUserFor(accountCreateRequestDTO).expectReturnCode(204)
                .then().userLoginsWith(accountCreateRequestDTO).expectReturnCode(200)
                .then().adminUserTriesTodeleteUser()
                .expectReturnCode(200)
                .then().userShouldNotExistInDatabase();
    }

    @Test
    void regularUserShouldNotBeAbleToCreateDuplicateAccount() {
        AccountCreateRequestDTO accountCreateRequestDTO = TestObjectBuilder.userAccountCreateRequestDTO();

        scenarioExecutor
                .when().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(204)
                .then().userIsCreatedFor(accountCreateRequestDTO).expectReturnCode(403);
    }
}
