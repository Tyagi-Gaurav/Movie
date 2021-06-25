package com.gt.scr.movie.test.steps;

import com.gt.scr.movie.test.domain.ImmutableTestTimezoneCreateRequestDTO;
import com.gt.scr.movie.test.domain.TestLoginResponseDTO;
import com.gt.scr.movie.test.domain.TestTimezoneCreateRequestDTO;
import com.gt.scr.movie.test.domain.TestTimezoneDTO;
import com.gt.scr.movie.test.domain.TestTimezonesDTO;
import com.gt.scr.movie.test.resource.ResponseHolder;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseSteps implements En {
    @Autowired
    private ResponseHolder responseHolder;

    public ResponseSteps() {
        Then("^the response should be received with HTTP status code (\\d+)$", (Integer responseCode) -> {
            assertThat(responseHolder.getResponseCode()).isEqualTo(responseCode);
        });

        And("^the response should be a success status response$", () -> {
            String response = responseHolder.readResponse(String.class);
            assertThat(response).isEqualTo("OK");
        });

        And("^the user login response contains an authorisation token$", () -> {
            TestLoginResponseDTO testLoginResponseDTO = responseHolder.readResponse(TestLoginResponseDTO.class);
            assertThat(testLoginResponseDTO.token()).isNotEmpty();
        });

        And("^the timezone read response contains the following timezones$", (DataTable datatable) -> {
            List<TestTimezoneCreateRequestDTO> timezoneCreateRequestDTOS = datatable.asList(TestTimezoneCreateRequestDTO.class);
            TestTimezonesDTO testTimezonesDTO = responseHolder.readResponse(TestTimezonesDTO.class);
            List<TestTimezoneDTO> timezones = testTimezonesDTO.timezones();
            assertThat(timezones).isNotEmpty();

            List<TestTimezoneCreateRequestDTO> actual = timezones.stream().map(tz -> ImmutableTestTimezoneCreateRequestDTO.builder()
                    .name(tz.name())
                    .city(tz.city())
                    .gmtOffset(tz.gmtOffset())
                    .build()).collect(Collectors.toList());


            assertThat(actual).hasSameElementsAs(timezoneCreateRequestDTOS);
        });


        And("^the response contains the (.*) header in response$", (String headerName) -> {
            assertThat(responseHolder.getHeaders().containsKey(headerName)).isTrue();
        });
    }
}
