package com.toptal.scr.tz.test.steps;

import com.toptal.scr.tz.test.resource.TimeZoneHealthCheckResource;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

public class HealthCheckSteps implements En {
    @Autowired
    private TimeZoneHealthCheckResource timeZoneHealthCheckResource;

    public HealthCheckSteps() {
        When("^the status endpoint is invoked$", () -> {
            timeZoneHealthCheckResource.invokeStatus();
        });
    }
}
