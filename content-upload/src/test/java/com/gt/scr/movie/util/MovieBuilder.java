package com.gt.scr.movie.util;

import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.service.domain.Movie;

import java.math.BigDecimal;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class MovieBuilder {

    private String name = randomAlphabetic(10);
    private BigDecimal rating = BigDecimal.valueOf(9.5);
    private int yearProduced = 2010;
    private long creationTimeStamp = System.nanoTime();
    private UUID id = UUID.randomUUID();

    private MovieBuilder() {}

    public static MovieBuilder aMovie() {
        return new MovieBuilder();
    }

    public static MovieBuilder copyOf(Movie movieA) {
        MovieBuilder movieBuilder = new MovieBuilder();

        movieBuilder.name = movieA.name();
        movieBuilder.rating = movieA.rating();
        movieBuilder.creationTimeStamp = movieA.creationTimeStamp();
        movieBuilder.yearProduced = movieA.yearProduced();
        movieBuilder.id = movieA.id();

        return movieBuilder;
    }

    public MovieBuilder withYearProduced(int yearProduced) {
        this.yearProduced = yearProduced;
        return this;
    }

    public MovieBuilder withRating(BigDecimal rating) {
        this.rating = rating;
        return this;
    }

    public MovieBuilder withName(String newName) {
        this.name = newName;
        return this;
    }

    public MovieBuilder withCreationTimeStamp(long nanoTime) {
        this.creationTimeStamp = nanoTime;
        return this;
    }

    public Movie build() {
        return new Movie(this.id,
                this.name,
                this.yearProduced,
                this.rating,
                this.creationTimeStamp);
    }

    public MovieDTO buildMovieDto() {
        return new MovieDTO(this.id,
                this.name,
                this.yearProduced,
                this.rating);
    }
}
