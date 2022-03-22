package com.gt.scr.movie.resource.domain;

import com.gt.scr.movie.util.MovieBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MoviesDTOTest {
    @Test
    void shouldReturnAnUnModifiableCopyOfList() {
        MovieDTO movie1DTO = MovieBuilder.aMovie().buildMovieDto();
        MovieDTO movie2DTO = MovieBuilder.aMovie().buildMovieDto();

        List<MovieDTO> movieDtoList = new ArrayList<>();
        movieDtoList.add(movie1DTO);
        movieDtoList.add(movie2DTO);

        MoviesDTO moviesDTO = new MoviesDTO(movieDtoList);

        //when
        List<MovieDTO> expectedMovies = moviesDTO.movies();

        //then
        MovieDTO newMovie = MovieBuilder.aMovie().buildMovieDto();
        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> expectedMovies.add(newMovie));
    }
}