package com.gt.scr.user;

import com.gt.scr.user.functions.DeleteUser;
import com.gt.scr.user.functions.FindUserById;
import com.gt.scr.user.functions.FindUserByName;
import com.gt.scr.user.functions.Login;
import com.gt.scr.user.functions.UserCreate;
import com.gt.scr.user.functions.UserManagementReadUsers;
import com.gt.scr.user.functions.VerifyUserDoesNotExist;
import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.resource.domain.LoginRequestDTO;
import com.gt.scr.user.resource.domain.LoginResponseDTO;
import com.gt.scr.user.resource.domain.UserDetailsResponseDTO;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioExecutor {
    private WebTestClient.ResponseSpec responseSpec;
    private LoginResponseDTO userLoginResponseDTO;

    private final WebTestClient webTestClient;
    private final DataSource dataSource;

    public ScenarioExecutor(WebTestClient webTestClient,
                            DataSource dataSource) {
        this.webTestClient = webTestClient;
        this.dataSource = dataSource;
    }

    public ScenarioExecutor when() {
        return this;
    }

    public ScenarioExecutor then() {
        return this;
    }

    public ScenarioExecutor userIsCreatedFor(AccountCreateRequestDTO accountCreateRequestDTO) {
        this.responseSpec = new UserCreate().apply(webTestClient, accountCreateRequestDTO);
        responseSpec.returnResult(Void.class).getResponseBody().blockFirst();
        return this;
    }

    public ScenarioExecutor expectReturnCode(int expectedStatus) {
        responseSpec.expectStatus().isEqualTo(expectedStatus);
        return this;
    }

    public ScenarioExecutor userLoginsWith(LoginRequestDTO loginRequestDTO) {
        this.responseSpec = new Login().apply(webTestClient, loginRequestDTO);
        this.userLoginResponseDTO = this.responseSpec.returnResult(LoginResponseDTO.class)
                .getResponseBody().blockFirst();
        return this;
    }

    public ScenarioExecutor userRetrievesListOfAllUsers() {
        this.responseSpec = new UserManagementReadUsers().apply(webTestClient, userLoginResponseDTO);
        return this;
    }

    public ScenarioExecutor retrieveUserByName(String userName) {
        this.responseSpec = new FindUserByName(userName).apply(webTestClient);
        return this;
    }

    public ScenarioExecutor retrieveUserById(UUID userId) {
        this.responseSpec = new FindUserById(userId).apply(webTestClient);
        return this;
    }

    public ScenarioExecutor retrievedUsersFirstNameIs(String firstName) {
        UserDetailsResponseDTO userDetailsResponseDTO = this.responseSpec.returnResult(UserDetailsResponseDTO.class)
                .getResponseBody().blockFirst();
        assertThat(userDetailsResponseDTO).isNotNull();
        assertThat(userDetailsResponseDTO.firstName()).isEqualTo(firstName);
        return this;
    }

    public ScenarioExecutor deleteUser() {
        this.responseSpec = new DeleteUser(userLoginResponseDTO.id()).apply(webTestClient);
        return this;
    }

    public ScenarioExecutor userShouldNotExistInDatabase() {
        new VerifyUserDoesNotExist().accept(dataSource, userLoginResponseDTO);
        return this;
    }
}
