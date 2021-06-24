package com.gt.scr.movie.test.steps;

import com.gt.scr.movie.test.config.TestConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = TestConfiguration.class)
public class GlueConfiguration {
}