package com.gt.scr.movie.test.steps;

import com.gt.scr.movie.test.config.ScenarioContext;
import com.gt.scr.movie.test.domain.TestAccountCreateRequestDTO;
import com.gt.scr.movie.test.domain.TestLoginRequestDTO;
import com.gt.scr.movie.test.resource.ResponseHolder;
import com.gt.scr.movie.test.resource.TestAccountCreateResource;
import com.gt.scr.movie.test.resource.TestLoginResource;
import com.gt.scr.movie.test.resource.TestUserManagementResource;
import io.cucumber.java8.En;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static com.gt.scr.movie.test.util.StringValueScenario.actualOrRandom;
import static org.assertj.core.api.Assertions.assertThat;

public class UserSteps implements En {
    @Autowired
    private TestAccountCreateResource testAccountCreateResource;

    @Autowired
    private TestLoginResource testLoginResource;

    @Autowired
    private TestUserManagementResource userManagementResource;

    @Autowired
    private ScenarioContext scenarioContext;

    @Autowired
    private ResponseHolder responseHolder;

    public UserSteps() {
        Given("^a user attempts to create a new account with following details$",
                (TestAccountCreateRequestDTO testAccountCreateRequestDTO) -> {
                    String userNameValue = testAccountCreateRequestDTO.userName();

                    if ("<captured>".equals(userNameValue)) {
                        userNameValue = scenarioContext.getLastUserName();
                    }

                    testAccountCreateRequestDTO = new TestAccountCreateRequestDTO(
                            actualOrRandom(userNameValue, 6),
                            actualOrRandom(testAccountCreateRequestDTO.password(), 6),
                            testAccountCreateRequestDTO.firstName(),
                            testAccountCreateRequestDTO.lastName(),
                            testAccountCreateRequestDTO.role()
                    );

                    scenarioContext.storeCredentialsRequest(testAccountCreateRequestDTO);
                    testAccountCreateResource.create(testAccountCreateRequestDTO);
                });

        When("^the user attempts to login using the new credentials$", () -> {
            loginUsing(scenarioContext.getUserCredentialsRequest());
        });

        When("^the user attempts to login using random password$", () -> {
            TestAccountCreateRequestDTO userCredentialsRequest = scenarioContext.getUserCredentialsRequest();
            TestLoginRequestDTO testLoginRequestDTO = new TestLoginRequestDTO(
                    userCredentialsRequest.userName(),
                    RandomStringUtils.randomAlphabetic(7));
            testLoginResource.create(testLoginRequestDTO);
        });

        Given("^a user creates a new account and performs login with user name '(.*)' and role '(.*)'$",
                (String userName, String role) -> {
                    TestAccountCreateRequestDTO testAccountCreateRequestDTO = new TestAccountCreateRequestDTO(
                            actualOrRandom(userName, 6),
                            RandomStringUtils.randomAlphabetic(6),
                            RandomStringUtils.randomAlphabetic(6),
                            RandomStringUtils.randomAlphabetic(6),
                            role);

                    scenarioContext.storeCredentialsRequest(testAccountCreateRequestDTO);
                    testAccountCreateResource.create(testAccountCreateRequestDTO);

                    loginUsing(scenarioContext.getCredentials(role));
                    assertThat(responseHolder.getResponseCode()).isEqualTo(200);
                });

        And("^the admin user attempts to login again$", () -> {
            loginUsing(scenarioContext.getAdminCredentialsRequest());
        });

        And("^the userId for the user is recorded$", () -> {
            scenarioContext.setUserIdForRegularUser(responseHolder.getUserId());
        });
        And("^the regular user attempts to login again$", () -> {
            loginUsing(scenarioContext.getUserCredentialsRequest());
        });

        When("the authenticated admin user creates another user with user name {string} and role {string}",
                (String userName, String role) -> {
                    TestAccountCreateRequestDTO testAccountCreateRequestDTO = new TestAccountCreateRequestDTO(
                            actualOrRandom(userName, 6),
                            RandomStringUtils.randomAlphabetic(6),
                            RandomStringUtils.randomAlphabetic(6),
                            RandomStringUtils.randomAlphabetic(6),
                            role);

                    scenarioContext.storeCredentialsRequest(testAccountCreateRequestDTO);
                    userManagementResource.create(testAccountCreateRequestDTO);
                });

        When("^the authenticated admin user deletes the previously created regular user$", () -> {
            userManagementResource.delete(scenarioContext.getRegularUserId());
        });

        And("^the userName is captured$", () -> {
            scenarioContext.setLastUserName(scenarioContext.getUserCredentialsRequest().userName());
        });


    }

    private void loginUsing(TestAccountCreateRequestDTO userCredentialsRequest) {
        TestLoginRequestDTO testLoginRequestDTO = new TestLoginRequestDTO(
                userCredentialsRequest.userName(),
                userCredentialsRequest.password());
        testLoginResource.create(testLoginRequestDTO);
    }
}
