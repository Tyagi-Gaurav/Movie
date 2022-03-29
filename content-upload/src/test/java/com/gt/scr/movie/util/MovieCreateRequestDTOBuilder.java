package com.gt.scr.movie.util;

import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.service.domain.AgeRating;
import com.gt.scr.movie.service.domain.ContentType;
import com.gt.scr.movie.service.domain.Genre;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

public class MovieCreateRequestDTOBuilder {
    private String name = RandomStringUtils.randomAlphabetic(7);
    private int yearProduced = 2010;
    private BigDecimal rating = BigDecimal.valueOf(5.6);
    private Genre genre = Genre.ACTION;
    private ContentType contentType = ContentType.MOVIE;
    private AgeRating ageRating = AgeRating.PG;
    private boolean isShareable = true;

    public static MovieCreateRequestDTOBuilder aMovieCreateRequest() {
        return new MovieCreateRequestDTOBuilder();
    }

    public MovieCreateRequestDTOBuilder withName(String movieName) {
        this.name = movieName;
        return this;
    }

    public MovieCreateRequestDTOBuilder withGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public MovieCreateRequestDTO build() {
        return new MovieCreateRequestDTO(name, yearProduced, rating, genre, contentType, ageRating, isShareable);
    }

    public MovieCreateRequestDTOBuilder withContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public MovieCreateRequestDTOBuilder withAgeRating(AgeRating ageRating) {
        this.ageRating = ageRating;
        return this;
    }
}
