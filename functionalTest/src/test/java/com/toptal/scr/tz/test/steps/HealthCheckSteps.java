package com.toptal.scr.tz.test.steps;

import com.toptal.scr.tz.test.resource.TestHealthCheckResource;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

public class HealthCheckSteps implements En {
    @Autowired
    private TestHealthCheckResource testHealthCheckResource;

    public HealthCheckSteps() {
        When("^the status endpoint is invoked$", () -> {
            testHealthCheckResource.invokeStatus();
        });
    }
}
