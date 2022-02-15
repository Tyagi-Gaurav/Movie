package com.gt.scr.user;

import com.gt.scr.user.functions.DeleteUser;
import com.gt.scr.user.functions.FindUserById;
import com.gt.scr.user.functions.FindUserByName;
import com.gt.scr.user.functions.Login;
import com.gt.scr.user.functions.Interaction;
import com.gt.scr.user.functions.UserCreate;
import com.gt.scr.user.functions.UserManagementCreateUser;
import com.gt.scr.user.functions.UserManagementReadUsers;
import com.gt.scr.user.functions.VerifyUserDoesNotExist;
import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.resource.domain.LoginRequestDTO;
import com.gt.scr.user.resource.domain.LoginResponseDTO;
import com.gt.scr.user.resource.domain.UserDetailsResponseDTO;
import com.gt.scr.utils.DataEncoder;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.util.encoders.HexEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioExecutor {
    private final WebTestClient webTestClient;
    private final DataSource dataSource;
    private WebTestClient.ResponseSpec responseSpec;
    private LoginResponseDTO userLoginResponseDTO;
    private LoginResponseDTO adminUserLoginResponseDTO;
    private final String adminPassword = UUID.randomUUID().toString();
    private final String adminUserName = "admin-" + RandomStringUtils.randomAscii(5);

    public ScenarioExecutor(WebTestClient webTestClient, DataSource dataSource) {
        this.webTestClient = webTestClient;
        this.dataSource = dataSource;
    }

    public void addAdminUser(DataSource dataSource) {
        DataEncoder dataEncoder = new DataEncoder(new HexEncoder());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO USER_SCHEMA.USER_TABLE (ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES) values (?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, adminUserName);
            preparedStatement.setString(3, UUID.randomUUID().toString());
            preparedStatement.setString(4, UUID.randomUUID().toString());
            preparedStatement.setString(5, dataEncoder.encode(adminPassword));
            preparedStatement.setString(6, "ADMIN");

            preparedStatement.execute();

        } catch (SQLException | IOException throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public ScenarioExecutor when() {
        return this;
    }

    public ScenarioExecutor then() {
        return this;
    }

    public ScenarioExecutor userIsCreatedFor(AccountCreateRequestDTO accountCreateRequestDTO) {
        this.responseSpec = new UserCreate().apply(webTestClient, accountCreateRequestDTO);
        return this;
    }

    public ScenarioExecutor expectReturnCode(int expectedStatus) {
        responseSpec.expectStatus().isEqualTo(expectedStatus);
        return this;
    }

    public ScenarioExecutor userLoginsWith(AccountCreateRequestDTO accountCreateRequestDTO) {
        return userLoginsWith(TestObjectBuilder.loginRequestUsing(accountCreateRequestDTO));
    }

    public ScenarioExecutor userLoginsWith(LoginRequestDTO loginRequestDTO) {
        this.responseSpec = new Login().apply(webTestClient, loginRequestDTO);
        this.userLoginResponseDTO = this.responseSpec.returnResult(LoginResponseDTO.class)
                .getResponseBody().blockFirst();
        return this;
    }

    public ScenarioExecutor regularUserRetrievesListOfAllUsers() {
        this.responseSpec = new UserManagementReadUsers().apply(webTestClient, userLoginResponseDTO);
        return this;
    }

    public ScenarioExecutor adminUserRetrievesListOfAllUsers() {
        this.responseSpec = new UserManagementReadUsers().apply(webTestClient, adminUserLoginResponseDTO);
        return this;
    }

    public ScenarioExecutor globalAdminUserLogins() {
        this.responseSpec = new Login().apply(webTestClient, new LoginRequestDTO(adminUserName, adminPassword));
        this.adminUserLoginResponseDTO = this.responseSpec.returnResult(LoginResponseDTO.class)
                .getResponseBody().blockFirst();
        return this;
    }

    public ScenarioExecutor adminUserCreatesARegularUserFor(AccountCreateRequestDTO accountCreateRequestDTO) {
        this.responseSpec = new UserManagementCreateUser()
                .apply(webTestClient, adminUserLoginResponseDTO, accountCreateRequestDTO);
        return this;
    }

    public ScenarioExecutor retrieveUserByNameForRegularUser(String userName) {
        this.responseSpec = new FindUserByName(userName).apply(webTestClient, userLoginResponseDTO);
        return this;
    }

    public ScenarioExecutor retrieveUserByNameForAdminUser(String userName) {
        this.responseSpec = new FindUserByName(userName).apply(webTestClient, adminUserLoginResponseDTO);
        return this;
    }

    public ScenarioExecutor retrieveUserById(UUID userId) {
        this.responseSpec = new FindUserById(userId).apply(webTestClient);
        return this;
    }

    public ScenarioExecutor retrieveUserByNameForRegularUserUsingToken(String userName, String token) {
        this.responseSpec = new FindUserByName(userName).apply(webTestClient, new LoginResponseDTO(token, UUID.randomUUID()));
        return this;
    }

    public ScenarioExecutor retrievedUsersFirstNameIs(String firstName) {
        UserDetailsResponseDTO userDetailsResponseDTO = this.responseSpec.returnResult(UserDetailsResponseDTO.class)
                .getResponseBody().blockFirst();
        assertThat(userDetailsResponseDTO).isNotNull();
        assertThat(userDetailsResponseDTO.firstName()).isEqualTo(firstName);
        return this;
    }

    public ScenarioExecutor adminUserTriesTodeleteUser() {
        this.responseSpec = new DeleteUser(userLoginResponseDTO).apply(webTestClient, adminUserLoginResponseDTO);
        return this;
    }

    public ScenarioExecutor userShouldNotExistInDatabase() {
        new VerifyUserDoesNotExist().accept(dataSource, userLoginResponseDTO);
        return this;
    }
}
