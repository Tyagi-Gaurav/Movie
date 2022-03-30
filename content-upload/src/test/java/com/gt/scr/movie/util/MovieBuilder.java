package com.gt.scr.movie.util;

import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.service.domain.AgeRating;
import com.gt.scr.movie.service.domain.ContentType;
import com.gt.scr.movie.service.domain.Genre;
import com.gt.scr.movie.service.domain.Movie;

import java.math.BigDecimal;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class MovieBuilder {

    private String name = randomAlphabetic(10);
    private BigDecimal rating = BigDecimal.valueOf(9.5);
    private int yearProduced = 2010;
    private long creationTimeStamp = System.nanoTime();
    private UUID movieId = UUID.randomUUID();
    private AgeRating ageRating = AgeRating.PG;
    private Genre genre  = Genre.ROMANCE;
    private ContentType contentType  = ContentType.MOVIE;
    private boolean isShareable  = true;

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
        movieBuilder.movieId = movieA.id();
        movieBuilder.ageRating = movieA.ageRating();
        movieBuilder.genre = movieA.genre();
        movieBuilder.isShareable = movieA.isShareable();
        movieBuilder.contentType = movieA.contentType();

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

    public MovieBuilder withGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public Movie build() {
        return new Movie(this.movieId,
                this.name,
                this.yearProduced,
                this.rating,
                this.genre,
                this.contentType,
                this.ageRating,
                this.isShareable,
                this.creationTimeStamp);
    }

    public MovieDTO buildMovieDto() {
        return new MovieDTO(this.movieId,
                this.name,
                this.yearProduced,
                this.rating,
                this.genre,
                this.contentType,
                this.ageRating,
                this.isShareable);
    }

    public MovieBuilder withAgeRating(AgeRating ageRating) {
        this.ageRating = ageRating;
        return this;
    }

    public MovieBuilder withContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public MovieBuilder withIsShareable(boolean isShareable) {
        this.isShareable = isShareable;
        return this;
    }

    public MovieBuilder withMovieId(UUID id) {
        this.movieId = id;
        return this;
    }
}
