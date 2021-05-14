package com.toptal.scr.tz.test.steps;

import com.toptal.scr.tz.test.config.TestConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = TestConfiguration.class)
public class GlueConfiguration {
}