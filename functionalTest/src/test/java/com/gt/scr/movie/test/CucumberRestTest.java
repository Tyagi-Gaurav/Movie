package com.gt.scr.movie.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        plugin = {"pretty", "json:target/cucumber-report.json"},
        tags = "not @Migrated",
        glue = {"com.gt.scr.movie.test.steps"},
        monochrome = true
)
public class CucumberRestTest {
    @BeforeClass
    public static void beforeClass() throws Exception {
        System.clearProperty("protocol");
    }
}

