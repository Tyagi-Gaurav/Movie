package com.toptal.scr.tz.test.steps;

import com.toptal.scr.tz.test.domain.TestTimezoneCreateRequestDTO;
import com.toptal.scr.tz.test.resource.TestTimezoneResource;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

public class TimezoneSteps implements En {

    @Autowired
    private TestTimezoneResource testTimezoneResource;

    public TimezoneSteps() {
        And("the authenticated user attempts to create a new timezone",
                (TestTimezoneCreateRequestDTO testTimezoneCreateRequestDTO) -> testTimezoneResource.create(testTimezoneCreateRequestDTO));
    }


}
