package com.toptal.scr.tz.test.steps;

import com.toptal.scr.tz.test.config.ScenarioContext;
import com.toptal.scr.tz.test.domain.ImmutableTestAccountCreateRequestDTO;
import com.toptal.scr.tz.test.domain.ImmutableTestLoginRequestDTO;
import com.toptal.scr.tz.test.domain.TestAccountCreateRequestDTO;
import com.toptal.scr.tz.test.domain.TestLoginRequestDTO;
import com.toptal.scr.tz.test.resource.TestAccountCreateResource;
import com.toptal.scr.tz.test.resource.TestLoginResource;
import io.cucumber.java8.En;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSteps implements En {
    @Autowired
    private TestAccountCreateResource testAccountCreateResource;

    @Autowired
    private TestLoginResource testLoginResource;

    @Autowired
    private ScenarioContext scenarioContext;

    public UserSteps() {
        Given("^a user attempts to create a new account with following details$",
                (TestAccountCreateRequestDTO testAccountCreateRequestDTO) -> {
                    if ("<random>".equals(testAccountCreateRequestDTO.userName())) {
                        testAccountCreateRequestDTO = ImmutableTestAccountCreateRequestDTO.builder().from(testAccountCreateRequestDTO)
                                .userName(RandomStringUtils.randomAlphabetic(6))
                                .password(RandomStringUtils.randomAlphabetic(6))
                                .build();
                    }

                    scenarioContext.setUserCredentialsRequest(testAccountCreateRequestDTO);
                    testAccountCreateResource.create(testAccountCreateRequestDTO);
                });

        When("^the user attempts to login using the new credentials$", () -> {
            TestAccountCreateRequestDTO userCredentialsRequest = scenarioContext.getUserCredentialsRequest();
            TestLoginRequestDTO testLoginRequestDTO = ImmutableTestLoginRequestDTO.builder()
                    .userName(userCredentialsRequest.userName())
                    .password(userCredentialsRequest.password())
                    .build();
            testLoginResource.create(testLoginRequestDTO);
        });

        When("^the user attempts to login using random password$", () -> {
            TestAccountCreateRequestDTO userCredentialsRequest = scenarioContext.getUserCredentialsRequest();
            TestLoginRequestDTO testLoginRequestDTO = ImmutableTestLoginRequestDTO.builder()
                    .userName(userCredentialsRequest.userName())
                    .password(RandomStringUtils.randomAlphabetic(6))
                    .build();
            testLoginResource.create(testLoginRequestDTO);
        });
    }
}
