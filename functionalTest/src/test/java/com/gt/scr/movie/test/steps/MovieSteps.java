package com.gt.scr.movie.test.steps;

import com.gt.scr.movie.test.config.ScenarioContext;
import com.gt.scr.movie.test.domain.ImmutableTestMovieCreateRequestDTO;
import com.gt.scr.movie.test.domain.ImmutableTestMovieUpdateRequestDTO;
import com.gt.scr.movie.test.domain.ImmutableTestTimezoneUpdateRequestDTO;
import com.gt.scr.movie.test.domain.TestMovieCreateRequestDTO;
import com.gt.scr.movie.test.domain.TestMovieDTO;
import com.gt.scr.movie.test.domain.TestMoviesDTO;
import com.gt.scr.movie.test.domain.TestTimezoneDTO;
import com.gt.scr.movie.test.domain.TestTimezoneUpdateRequestDTO;
import com.gt.scr.movie.test.domain.TestTimezonesDTO;
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
                    testMovieCreateRequestDTOS.forEach(testMovieResource::create);
                });

        When("^the authenticated user attempts to read the movies", () -> {
            testMovieResource.readMovies();
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

            List<TestMovieCreateRequestDTO> actual = movies.stream().map(mv -> ImmutableTestMovieCreateRequestDTO.builder()
                    .name(mv.name())
                    .rating(mv.rating())
                    .yearProduced(mv.yearProduced())
                    .build()).collect(Collectors.toList());


            assertThat(actual).hasSameElementsAs(movieCreateRequestDTOS);
        });

        When("^the user attempts to delete the movie with name: '(.*)' , yearProduced: '(\\d+)' and rating: '(.*)'$",
                (String name, Integer yearProduced, BigDecimal rating) -> {
                    testMovieResource.readMovies();
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

                    testMovieResource.readMovies();
                    var testMoviesDTO = responseHolder.readResponse(TestMoviesDTO.class);
                    var movies = testMoviesDTO.movies();

                    UUID uuid = movies.stream()
                            .filter(mv -> name.equals(mv.name()))
                            .findFirst().map(TestMovieDTO::id).orElseThrow(IllegalStateException::new);

                    var updateRequestDTO =
                            ImmutableTestMovieUpdateRequestDTO.builder()
                                    .name(testMovieCreateRequestDTO.name())
                                    .rating(testMovieCreateRequestDTO.rating())
                                    .yearProduced(testMovieCreateRequestDTO.yearProduced())
                                    .id(uuid)
                                    .build();

                    testMovieResource.updateMovie(updateRequestDTO);
                });

        When("^the user attempts to update the movie with name: '(.*)' to '(.*)'$",
                (String fromMovieName, String toMovieName) -> {
                    testMovieResource.readMovies();
                    var testMoviesDTO = responseHolder.readResponse(TestMoviesDTO.class);
                    var movies = testMoviesDTO.movies();

                    TestMovieDTO testMovieDTO = movies.stream()
                            .filter(mv -> fromMovieName.equals(mv.name()))
                            .findFirst().orElseThrow(IllegalStateException::new);

                    var updateRequestDTO =
                            ImmutableTestMovieUpdateRequestDTO.builder()
                                    .name(toMovieName)
                                    .id(testMovieDTO.id())
                                    .build();

                    testMovieResource.updateMovie(updateRequestDTO);
                });
    }
}
