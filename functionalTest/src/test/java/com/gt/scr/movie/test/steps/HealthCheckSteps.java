package com.gt.scr.movie.test.steps;

import com.gt.scr.movie.test.resource.ResponseHolder;
import com.gt.scr.movie.test.resource.TestHealthCheckResource;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthCheckSteps implements En {
    @Autowired
    private TestHealthCheckResource testHealthCheckResource;

    @Autowired
    private ResponseHolder responseHolder;

    public HealthCheckSteps() {
        When("^the status endpoint is invoked$", () -> {
            testHealthCheckResource.invokeStatus();
        });

        When("^the healthcheck endpoint for movie is invoked$", () -> {
            testHealthCheckResource.invokeMovieHealthcheck();
        });

        And("^the response for movie healthcheck should be a success response$", () -> {
            String healthCheckResponse = responseHolder.readResponse(String.class);
            DocumentContext jsonParser = JsonPath.parse(healthCheckResponse);
            assertThat("UP").isEqualTo(jsonParser.read("$.status"));
            assertThat("UP").isEqualTo(jsonParser.read("$.components.db.status"));
            assertThat("UP").isEqualTo(jsonParser.read("$.components.diskSpace.status"));
            assertThat("UP").isEqualTo(jsonParser.read("$.components.ping.status"));
            assertThat("UP").isEqualTo(jsonParser.read("$.components.userApp.status"));
        });

        When("^the healthcheck endpoint for user is invoked$", () -> {
            testHealthCheckResource.invokeUserHealthcheck();
        });

        And("^the response for user healthcheck should be a success response$", () -> {
            String healthCheckResponse = responseHolder.readResponse(String.class);
            DocumentContext jsonParser = JsonPath.parse(healthCheckResponse);
            assertThat("UP").isEqualTo(jsonParser.read("$.status"));
            assertThat("UP").isEqualTo(jsonParser.read("$.components.db.status"));
            assertThat("UP").isEqualTo(jsonParser.read("$.components.diskSpace.status"));
            assertThat("UP").isEqualTo(jsonParser.read("$.components.ping.status"));
        });
    }
}
