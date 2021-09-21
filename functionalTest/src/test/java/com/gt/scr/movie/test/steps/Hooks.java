package com.gt.scr.movie.test.steps;

import com.gt.scr.movie.test.resource.AbstractResource;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractResource.class);

    @Before(value = "@grpc")
    public void beforeScenario() {
        LOG.info("Setting Protocol to GRPC");
        System.setProperty("protocol", "grpc");
    }

    @After
    public void afterScenario() {
        LOG.info("Reset Protocol");
        System.clearProperty("protocol");
    }
}
