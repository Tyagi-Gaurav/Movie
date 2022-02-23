package com.gt.scr.movie.test.steps;

import com.gt.scr.movie.test.config.ScenarioContext;
import com.gt.scr.movie.test.domain.TestMovieCreateRequestDTO;
import com.gt.scr.movie.test.domain.TestMovieCreateResponseDTO;
import com.gt.scr.movie.test.domain.TestMovieDTO;
import com.gt.scr.movie.test.domain.TestMovieUpdateRequestDTO;
import com.gt.scr.movie.test.domain.TestMoviesDTO;
import com.gt.scr.movie.test.resource.ResponseHolder;
import com.gt.scr.movie.test.resource.TestMovieResource;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MovieSteps implements En {

    @Autowired
    private TestMovieResource testMovieResource;

    @Autowired
    private ResponseHolder responseHolder;

    @Autowired
    private ScenarioContext scenarioContext;

    public MovieSteps() {

        And("the authenticated user attempts to create a new movie",
                (DataTable dataTable) -> {
                    List<TestMovieCreateRequestDTO> testMovieCreateRequestDTOS = dataTable.asList(TestMovieCreateRequestDTO.class);
                    testMovieCreateRequestDTOS.forEach(testMovieResource::createMovieFor);
                });

        When("^the authenticated user attempts to read the movies", () -> {
            testMovieResource.readMoviesFor();
        });

        And("^the authenticated user attempts to read the movies for the previous user$", () -> {
            String regularUserId = scenarioContext.getRegularUserId();
            testMovieResource.readMoviesFor(regularUserId);
        });

        And("^the authenticated user attempts to create a new movie without passing auth header$",
                (TestMovieCreateRequestDTO testMovieCreateRequestDTO) -> {
                    testMovieResource.createWithoutToken(testMovieCreateRequestDTO);
                });

        And("^the movie read response contains the following records$", (DataTable datatable) -> {
            List<TestMovieCreateRequestDTO> movieCreateRequestDTOS = datatable.asList(TestMovieCreateRequestDTO.class);
            TestMoviesDTO testMoviesDTO = responseHolder.readResponse(TestMoviesDTO.class);
            var movies = testMoviesDTO.movies();
            assertThat(movies).isNotEmpty();

            List<TestMovieCreateRequestDTO> actual = movies.stream().map(mv ->
                    new TestMovieCreateRequestDTO(mv.name(), mv.yearProduced(), mv.rating()))
                    .collect(Collectors.toList());


            assertThat(actual).hasSameElementsAs(movieCreateRequestDTOS);
        });

        When("^the user attempts to delete the movie with name: '(.*)' , yearProduced: '(\\d+)' and rating: '(.*)'$",
                (String name, Integer yearProduced, BigDecimal rating) -> {
                    testMovieResource.readMoviesFor();
                    TestMoviesDTO testMoviesDTO = responseHolder.readResponse(TestMoviesDTO.class);
                    var movies = testMoviesDTO.movies();

                    var uuid = movies.stream()
                            .filter(mv -> name.equals(mv.name()) &&
                                    rating.equals(mv.rating()) &&
                                    yearProduced.equals(mv.yearProduced()))
                            .findFirst().map(TestMovieDTO::id).orElseThrow(IllegalStateException::new);

                    testMovieResource.deleteMovie(uuid);
                });

        When("^the user attempts to update the movie with name: '(.*)' to$",
                (String name, TestMovieCreateRequestDTO testMovieCreateRequestDTO) -> {

                    testMovieResource.readMoviesFor();
                    var testMoviesDTO = responseHolder.readResponse(TestMoviesDTO.class);
                    var movies = testMoviesDTO.movies();

                    UUID uuid = movies.stream()
                            .filter(mv -> name.equals(mv.name()))
                            .findFirst().map(TestMovieDTO::id).orElseThrow(IllegalStateException::new);

                    var updateRequestDTO =
                            new TestMovieUpdateRequestDTO(
                                    uuid, testMovieCreateRequestDTO.name(),
                                    testMovieCreateRequestDTO.rating(),
                                    testMovieCreateRequestDTO.yearProduced()
                            );

                    testMovieResource.updateMovie(updateRequestDTO);
                });

        When("^the user attempts to update the movie with name: '(.*)' to '(.*)'$",
                (String fromMovieName, String toMovieName) -> {
                    testMovieResource.readMoviesFor();
                    var testMoviesDTO = responseHolder.readResponse(TestMoviesDTO.class);
                    var movies = testMoviesDTO.movies();

                    TestMovieDTO testMovieDTO = movies.stream()
                            .filter(mv -> fromMovieName.equals(mv.name()))
                            .findFirst().orElseThrow(IllegalStateException::new);

                    var updateRequestDTO = new TestMovieUpdateRequestDTO(testMovieDTO.id(), toMovieName);

                    testMovieResource.updateMovie(updateRequestDTO);
                });

        When("^the authenticated admin user attempts to read the movies for user$", () -> {
            String regularUserId = scenarioContext.getRegularUserId();
            testMovieResource.readMoviesFor(regularUserId);
        });

        When("^the authenticated admin user attempts to delete all the movies for user$", () -> {
            String regularUserId = scenarioContext.getRegularUserId();
            testMovieResource.readMoviesFor(regularUserId);
            var testMoviesDTO = responseHolder.readResponse(TestMoviesDTO.class);
            var timezones = testMoviesDTO.movies();

            timezones.forEach(mv -> testMovieResource.deleteMovie(mv.id(), regularUserId));
        });

        And("^the movie read response should be empty$", () -> {
            TestMoviesDTO testMoviesDTO = responseHolder.readResponse(TestMoviesDTO.class);
            List<TestMovieDTO> movies = testMoviesDTO.movies();

            assertThat(movies).isEmpty();
        });

        And("^the authenticated user attempts to create a new movie for the regular user$", (DataTable dataTable) -> {
            String regularUserId = scenarioContext.getRegularUserId();
            List<TestMovieCreateRequestDTO> testMovieCreateRequestDTO = dataTable.asList(TestMovieCreateRequestDTO.class);
            testMovieCreateRequestDTO.forEach(movieCreateRequestDTO ->
                    testMovieResource.createMovieFor(movieCreateRequestDTO, regularUserId));
        });

        When("^the admin user attempts to update the movie with name: '(.*)' to$",
                (String name, TestMovieCreateRequestDTO testMovieCreateRequestDTO) -> {
                    String regularUserId = scenarioContext.getRegularUserId();
                    testMovieResource.readMoviesFor(regularUserId);
                    var testMoviesDTO = responseHolder.readResponse(TestMoviesDTO.class);
                    var movies = testMoviesDTO.movies();

                    UUID uuid = movies.stream()
                            .filter(mv -> name.equals(mv.name()))
                            .findFirst()
                            .map(TestMovieDTO::id).orElseThrow(IllegalStateException::new);

                    var updateRequestDTO =
                            new TestMovieUpdateRequestDTO(uuid, testMovieCreateRequestDTO.name(),
                                    testMovieCreateRequestDTO.rating(),
                                    testMovieCreateRequestDTO.yearProduced());

                    testMovieResource.updateMovie(updateRequestDTO, regularUserId);
                });

        And("^the movie-id of the movie is recorded$", () -> {
            //scenarioContext.setMovieId()
        });

        And("^the response should contain a movieId in a UUID format$", () -> {
            TestMovieCreateResponseDTO testMovieCreateResponseDTO =
                    responseHolder.readResponse(TestMovieCreateResponseDTO.class);
            assertThat(testMovieCreateResponseDTO.movieId()).isNotNull();
        });
    }
}
