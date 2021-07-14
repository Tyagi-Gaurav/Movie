package com.gt.scr.movie.test.steps;

import com.gt.scr.movie.test.domain.TestLoginResponseDTO;
import com.gt.scr.movie.test.resource.ResponseHolder;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseSteps implements En {
    @Autowired
    private ResponseHolder responseHolder;

    public ResponseSteps() {
        Then("^the response should be received with HTTP status code (\\d+)$", (Integer responseCode) -> {
            System.out.println("responseHolder.getResponseCode(): " + responseHolder.getResponseCode());
            assertThat(responseHolder.getResponseCode()).isEqualTo(responseCode);
        });

        And("^the response should have a success status$", () -> {
            String response = responseHolder.readResponse(String.class);
            assertThat(response).isEqualTo("OK");
        });

        And("^the user login response contains an authorisation token$", () -> {
            TestLoginResponseDTO testLoginResponseDTO = responseHolder.readResponse(TestLoginResponseDTO.class);
            assertThat(testLoginResponseDTO.token()).isNotEmpty();
        });

        And("^the response contains the (.*) header in response$", (String headerName) -> {
            assertThat(responseHolder.getHeaders().containsKey(headerName)).isTrue();
        });
    }
}
