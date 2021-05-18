package com.toptal.scr.tz.test.steps;

import com.toptal.scr.tz.test.config.ScenarioContext;
import com.toptal.scr.tz.test.domain.ImmutableTestTimezoneUpdateRequestDTO;
import com.toptal.scr.tz.test.domain.TestTimezoneCreateRequestDTO;
import com.toptal.scr.tz.test.domain.TestTimezoneDTO;
import com.toptal.scr.tz.test.domain.TestTimezoneUpdateRequestDTO;
import com.toptal.scr.tz.test.domain.TestTimezonesDTO;
import com.toptal.scr.tz.test.resource.ResponseHolder;
import com.toptal.scr.tz.test.resource.TestTimezoneResource;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TimezoneSteps implements En {

    @Autowired
    private TestTimezoneResource testTimezoneResource;

    @Autowired
    private ResponseHolder responseHolder;

    @Autowired
    private ScenarioContext scenarioContext;

    public TimezoneSteps() {
        And("the authenticated user attempts to create a new timezone",
                (DataTable dataTable) -> {
                    List<TestTimezoneCreateRequestDTO> testTimezoneCreateRequestDTO = dataTable.asList(TestTimezoneCreateRequestDTO.class);
                    testTimezoneCreateRequestDTO.forEach(testTimezoneResource::create);
                });

        When("^the authenticated user attempts to read the timezones$", () -> {
            testTimezoneResource.readTimezones();
        });

        When("^the user attempts to delete the timezone with name: '(.*)' , city: '(.*)' and gmtOffset: '(\\d+)'$",
                (String name, String city, Integer gmtOffset) -> {
                    testTimezoneResource.readTimezones();
                    TestTimezonesDTO testTimezonesDTO = responseHolder.readResponse(TestTimezonesDTO.class);
                    List<TestTimezoneDTO> timezones = testTimezonesDTO.timezones();

                    UUID uuid = timezones.stream()
                            .filter(tz -> name.equals(tz.name()) && city.equals(tz.city())
                                    && gmtOffset.equals(tz.gmtOffset()))
                            .findFirst().map(TestTimezoneDTO::id).orElseThrow(IllegalStateException::new);

                    testTimezoneResource.deleteTimezone(uuid);
                });

        When("^the user attempts to update the timezone with name: '(.*)' to$",
                (String name, TestTimezoneCreateRequestDTO testTimezoneCreateRequestDTO) -> {

                    testTimezoneResource.readTimezones();
                    TestTimezonesDTO testTimezonesDTO = responseHolder.readResponse(TestTimezonesDTO.class);
                    List<TestTimezoneDTO> timezones = testTimezonesDTO.timezones();

                    UUID uuid = timezones.stream()
                            .filter(tz -> name.equals(tz.name()))
                            .findFirst().map(TestTimezoneDTO::id).orElseThrow(IllegalStateException::new);

                    TestTimezoneUpdateRequestDTO updateRequestDTO =
                            ImmutableTestTimezoneUpdateRequestDTO.builder()
                                    .city(testTimezoneCreateRequestDTO.city())
                                    .gmtOffset(testTimezoneCreateRequestDTO.gmtOffset())
                                    .name(testTimezoneCreateRequestDTO.name())
                                    .id(uuid)
                                    .build();

                    testTimezoneResource.updateTimezone(updateRequestDTO);
                });
        When("^the authenticated admin user attempts to read the timezones for user$", () -> {
            String regularUserId = scenarioContext.getRegularUserId();
            testTimezoneResource.readTimezones(regularUserId);
        });

        When("^the authenticated admin user attempts to delete all the timezones for user$", () -> {
            String regularUserId = scenarioContext.getRegularUserId();
            testTimezoneResource.readTimezones(regularUserId);
            TestTimezonesDTO testTimezonesDTO = responseHolder.readResponse(TestTimezonesDTO.class);
            List<TestTimezoneDTO> timezones = testTimezonesDTO.timezones();

            timezones.forEach(tz -> testTimezoneResource.deleteTimezone(tz.id(), regularUserId));
        });

        And("^the timezone read response should be empty$", () -> {
            TestTimezonesDTO testTimezonesDTO = responseHolder.readResponse(TestTimezonesDTO.class);
            List<TestTimezoneDTO> timezones = testTimezonesDTO.timezones();

            assertThat(timezones).isEmpty();
        });

        And("^the authenticated user attempts to create a new timezone for the regular user$", (DataTable dataTable) -> {
            String regularUserId = scenarioContext.getRegularUserId();
            List<TestTimezoneCreateRequestDTO> testTimezoneCreateRequestDTO = dataTable.asList(TestTimezoneCreateRequestDTO.class);
            testTimezoneCreateRequestDTO.forEach(timezoneCreateRequestDTO ->
                    testTimezoneResource.create(timezoneCreateRequestDTO, regularUserId));
        });

        And("^the authenticated user attempts to read the timezones for the previous user$", () -> {
            String regularUserId = scenarioContext.getRegularUserId();
            testTimezoneResource.readTimezones(regularUserId);
        });
        When("^the admin user attempts to update the timezone with name: '(.*)' to$",
                (String name, TestTimezoneCreateRequestDTO testTimezoneCreateRequestDTO) -> {
                    String regularUserId = scenarioContext.getRegularUserId();
                    testTimezoneResource.readTimezones(regularUserId);
                    TestTimezonesDTO testTimezonesDTO = responseHolder.readResponse(TestTimezonesDTO.class);
                    List<TestTimezoneDTO> timezones = testTimezonesDTO.timezones();

                    UUID uuid = timezones.stream()
                            .filter(tz -> name.equals(tz.name()))
                            .findFirst().map(TestTimezoneDTO::id).orElseThrow(IllegalStateException::new);

                    TestTimezoneUpdateRequestDTO updateRequestDTO =
                            ImmutableTestTimezoneUpdateRequestDTO.builder()
                                    .city(testTimezoneCreateRequestDTO.city())
                                    .gmtOffset(testTimezoneCreateRequestDTO.gmtOffset())
                                    .name(testTimezoneCreateRequestDTO.name())
                                    .id(uuid)
                                    .build();

                    testTimezoneResource.updateTimezone(updateRequestDTO, regularUserId);
                });
    }


}
