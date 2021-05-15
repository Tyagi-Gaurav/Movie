package com.toptal.scr.tz.test.config;

import com.toptal.scr.tz.test.domain.TestAccountCreateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ScenarioContext {

    private TestAccountCreateRequestDTO testAccountCreateRequestDTO;

    public void setUserCredentialsRequest(TestAccountCreateRequestDTO testAccountCreateRequestDTO) {
        this.testAccountCreateRequestDTO = testAccountCreateRequestDTO;
    }


    public TestAccountCreateRequestDTO getUserCredentialsRequest() {
        return testAccountCreateRequestDTO;
    }
}