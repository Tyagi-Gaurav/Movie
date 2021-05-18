package com.toptal.scr.tz.test.steps;

import com.toptal.scr.tz.test.config.ScenarioContext;
import com.toptal.scr.tz.test.domain.ImmutableTestAccountCreateRequestDTO;
import com.toptal.scr.tz.test.domain.ImmutableTestLoginRequestDTO;
import com.toptal.scr.tz.test.domain.TestAccountCreateRequestDTO;
import com.toptal.scr.tz.test.domain.TestLoginRequestDTO;
import com.toptal.scr.tz.test.resource.ResponseHolder;
import com.toptal.scr.tz.test.resource.TestAccountCreateResource;
import com.toptal.scr.tz.test.resource.TestLoginResource;
import io.cucumber.java8.En;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static com.toptal.scr.tz.test.util.StringValueScenario.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserSteps implements En {
    @Autowired
    private TestAccountCreateResource testAccountCreateResource;

    @Autowired
    private TestLoginResource testLoginResource;

    @Autowired
    private ScenarioContext scenarioContext;

    @Autowired
    private ResponseHolder responseHolder;

    public UserSteps() {
        Given("^a user attempts to create a new account with following details$",
                (TestAccountCreateRequestDTO testAccountCreateRequestDTO) -> {
                    testAccountCreateRequestDTO = ImmutableTestAccountCreateRequestDTO.builder().from(testAccountCreateRequestDTO)
                            .userName(get(testAccountCreateRequestDTO.userName(), 6))
                            .password(get(testAccountCreateRequestDTO.password(), 6))
                            .build();

                    scenarioContext.storeCredentialsRequest(testAccountCreateRequestDTO);
                    testAccountCreateResource.create(testAccountCreateRequestDTO);
                });

        When("^the user attempts to login using the new credentials$", () -> {
            loginUsing(scenarioContext.getUserCredentialsRequest());
        });

        When("^the user attempts to login using random password$", () -> {
            TestAccountCreateRequestDTO userCredentialsRequest = scenarioContext.getUserCredentialsRequest();
            TestLoginRequestDTO testLoginRequestDTO = ImmutableTestLoginRequestDTO.builder()
                    .userName(userCredentialsRequest.userName())
                    .password(RandomStringUtils.randomAlphabetic(7))
                    .build();
            testLoginResource.create(testLoginRequestDTO);
        });

        Given("^a user creates a new account and performs login with user name '(.*)' and role '(.*)'$",
                (String userName, String role) -> {
                    TestAccountCreateRequestDTO testAccountCreateRequestDTO = ImmutableTestAccountCreateRequestDTO.builder()
                            .userName(get(userName, 6))
                            .password(RandomStringUtils.randomAlphabetic(6))
                            .role(role)
                            .firstName(RandomStringUtils.randomAlphabetic(6))
                            .lastName(RandomStringUtils.randomAlphabetic(6))
                            .build();

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
    }

    private void loginUsing(TestAccountCreateRequestDTO userCredentialsRequest) {
        TestLoginRequestDTO testLoginRequestDTO = ImmutableTestLoginRequestDTO.builder()
                .userName(userCredentialsRequest.userName())
                .password(userCredentialsRequest.password())
                .build();
        testLoginResource.create(testLoginRequestDTO);
    }
}
