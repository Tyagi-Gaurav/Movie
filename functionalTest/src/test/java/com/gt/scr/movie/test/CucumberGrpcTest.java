package com.gt.scr.movie.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        plugin = {"pretty", "json:target/cucumber-report.json"},
        glue = {"com.gt.scr.movie.test.steps"},
        tags = "@grpc",
        monochrome = true
)
public class CucumberGrpcTest {
    @BeforeClass
    public static void beforeClass() {
        System.setProperty("protocol", "grpc");
    }

    @AfterClass
    public static void afterClass() {
        System.clearProperty("protocol");
    }
}

