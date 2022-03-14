package com.gt.scr.user;

import com.gt.scr.domain.Gender;
import com.gt.scr.user.functions.DeleteUser;
import com.gt.scr.user.functions.FindUserById;
import com.gt.scr.user.functions.FindUserByName;
import com.gt.scr.user.functions.Login;
import com.gt.scr.user.functions.StatusCheck;
import com.gt.scr.user.functions.UserCreate;
import com.gt.scr.user.functions.UserManagementCreateUser;
import com.gt.scr.user.functions.UserManagementReadUsers;
import com.gt.scr.user.functions.VerifyUserDoesNotExist;
import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.resource.domain.LoginRequestDTO;
import com.gt.scr.user.resource.domain.LoginResponseDTO;
import com.gt.scr.user.resource.domain.UserDetailsResponseDTO;
import com.gt.scr.utils.DataEncoder;
import com.gt.scr.utils.DataEncoderImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.util.encoders.HexEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioExecutor {
    private final WebTestClient webTestClient;
    private final DataSource dataSource;
    private WebTestClient.ResponseSpec responseSpec;
    private LoginResponseDTO userLoginResponseDTO;
    private LoginResponseDTO adminUserLoginResponseDTO;
    private final String adminPassword = UUID.randomUUID().toString();
    private final String adminUserName = "admin-" + RandomStringUtils.randomAscii(5);
    private final Map<Class, Object> responses = new HashMap<>();

    public ScenarioExecutor(WebTestClient webTestClient, DataSource dataSource) {
        this.webTestClient = webTestClient;
        this.dataSource = dataSource;
    }

    public void addAdminUser(DataSource dataSource) {
        DataEncoder dataEncoder = new DataEncoderImpl(new HexEncoder());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO USER_SCHEMA.USER_TABLE (ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, DATE_OF_BIRTH, GENDER, HOME_COUNTRY, ROLES) values (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, adminUserName);
            preparedStatement.setString(3, UUID.randomUUID().toString());
            preparedStatement.setString(4, UUID.randomUUID().toString());
            preparedStatement.setString(5, dataEncoder.encode(adminPassword));
            preparedStatement.setString(6, "01/01/1980");
            preparedStatement.setString(7, Gender.MALE.toString());
            preparedStatement.setString(8, "AUS");
            preparedStatement.setString(9, "ADMIN");

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
        return userLoginsWith(LoginRequestBuilder.loginRequestUsing(accountCreateRequestDTO));
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
        this.responses.put(UserDetailsResponseDTO.class, responseSpec.returnResult(UserDetailsResponseDTO.class)
                .getResponseBody().blockFirst());
        return this;
    }

    public ScenarioExecutor retrieveUserByNameForAdminUser(String userName) {
        this.responseSpec = new FindUserByName(userName).apply(webTestClient, adminUserLoginResponseDTO);
        this.responses.put(UserDetailsResponseDTO.class, responseSpec.returnResult(UserDetailsResponseDTO.class)
                .getResponseBody().blockFirst());
        return this;
    }

    public ScenarioExecutor retrieveUserById(UUID userId) {
        this.responseSpec = new FindUserById(userId).apply(webTestClient);
        this.responses.put(UserDetailsResponseDTO.class, responseSpec.returnResult(UserDetailsResponseDTO.class)
                .getResponseBody().blockFirst());
        return this;
    }

    public ScenarioExecutor retrieveUserByNameForRegularUserUsingToken(String userName, String token) {
        this.responseSpec = new FindUserByName(userName).apply(webTestClient, new LoginResponseDTO(token, UUID.randomUUID()));
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

    public ScenarioExecutor checkStatusOfApplication() {
        this.responseSpec = new StatusCheck().apply(webTestClient);
        return this;
    }

    public ScenarioExecutor expectBodyToBeEqualTo(String expectedBodyAsString) {
        String actualResult = this.responseSpec.returnResult(String.class)
                .getResponseBody().blockFirst();
        assertThat(actualResult).isEqualTo(expectedBodyAsString);
        return this;
    }

    public ScenarioExecutor expectRequestIdIsReturnedInResponse() {
        this.responseSpec.expectHeader().exists("requestId");
        return this;
    }

    public ScenarioExecutor userLoginsWithCustomRequestId(AccountCreateRequestDTO accountCreateRequestDTO,
                                                          UUID requestId) {
        LoginRequestDTO loginRequestDTO = LoginRequestBuilder.loginRequestUsing(accountCreateRequestDTO);
        this.responseSpec = new Login(requestId).apply(webTestClient, loginRequestDTO);
        this.userLoginResponseDTO = this.responseSpec.returnResult(LoginResponseDTO.class)
                .getResponseBody().blockFirst();
        return this;
    }

    public void expectRequestIdIsReturnedInResponseIs(String requestId) {
        this.responseSpec.expectHeader().valueEquals("requestId", requestId);
    }

    public <T> ScenarioExecutor thenAssertThat(Consumer<T> responseSpecConsumer, Class<T> clazz) {
        T response = getResponseOfType(clazz);
        responseSpecConsumer.accept(response);
        return this;
    }

    private <T> T getResponseOfType(Class<T> clazz) {
        T response = (T) responses.get(clazz);
        return response;
    }

    public ScenarioExecutor retrieveUserFromDatabase(String userName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM USER_SCHEMA.USER_TABLE WHERE USER_NAME = ?")) {

            preparedStatement.setString(1, userName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                responses.put(UserDetailsResponseDTO.class, new UserDetailsResponseDTO(resultSet.getString("USER_NAME"),
                        resultSet.getString("PASSWORD"),
                        resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"),
                        resultSet.getString("ROLES"),
                        UUID.fromString(resultSet.getString("ID")),
                        resultSet.getString("DATE_OF_BIRTH"),
                        Gender.valueOf(resultSet.getString("GENDER")),
                        resultSet.getString("HOME_COUNTRY")));
            }
        } catch (SQLException throwable) {
            throw new RuntimeException(throwable);
        }
        return this;
    }
}
