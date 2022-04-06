package com.gt.scr.movie.test.steps;

import com.gt.scr.movie.test.resource.TestMetricsResource;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

public class MetricSteps implements En {
    @Autowired
    private TestMetricsResource testMetricsResource;

    public MetricSteps() {
        When("^the content upload metrics endpoint is invoked$", () -> {
            testMetricsResource.invokeMovieMetrics();
        });
    }
}
