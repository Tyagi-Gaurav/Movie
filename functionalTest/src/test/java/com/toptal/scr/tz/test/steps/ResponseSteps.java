package com.toptal.scr.tz.test.steps;

import com.toptal.scr.tz.test.domain.TestLoginResponseDTO;
import com.toptal.scr.tz.test.resource.ResponseHolder;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseSteps implements En {
    @Autowired
    private ResponseHolder responseHolder;

    public ResponseSteps() {
        Then("^the response should be received with HTTP status code (\\d+)$", (Integer responseCode) -> {
            assertThat(responseHolder.getResponseCode()).isEqualTo(responseCode);
        });

        And("^the response should be a success status response$", () -> {
            String response = responseHolder.readResponse(String.class);
            assertThat(response).isEqualTo("OK");
        });

        And("^the user login response contains an authorisation token$", () -> {
            TestLoginResponseDTO testLoginResponseDTO = responseHolder.readResponse(TestLoginResponseDTO.class);
            assertThat(testLoginResponseDTO.token()).isNotEmpty();
        });
    }
}
