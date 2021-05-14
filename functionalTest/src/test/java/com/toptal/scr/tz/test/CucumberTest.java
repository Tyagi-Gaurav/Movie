package com.toptal.scr.tz.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        plugin = {"pretty", "json:target/cucumber-report.json"},
        glue = {"com.toptal.scr.tz.test.steps"},
        monochrome = true
)
public class CucumberTest { }

