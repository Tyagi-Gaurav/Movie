package com.toptal.scr.tz.test.steps;

import com.toptal.scr.tz.test.domain.TestAccountCreateRequestDTO;
import com.toptal.scr.tz.test.resource.TestAccountCreateResource;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSteps implements En {
    @Autowired
    private TestAccountCreateResource testAccountCreateResource;

    public UserSteps() {
        Given("^a user attempts to create a new account with following details$",
                (TestAccountCreateRequestDTO testAccountCreateRequestDTO) -> {
            testAccountCreateResource.create(testAccountCreateRequestDTO);
        });
    }
}
