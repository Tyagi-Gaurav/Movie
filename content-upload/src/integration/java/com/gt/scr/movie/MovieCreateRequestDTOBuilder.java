package com.gt.scr.movie;

import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

public class MovieCreateRequestDTOBuilder {
    private String name = RandomStringUtils.randomAlphabetic(7);
    private int yearProduced = 2010;
    private BigDecimal rating = BigDecimal.valueOf(5.6);

    public static MovieCreateRequestDTOBuilder aMovieCreateRequest() {
        return new MovieCreateRequestDTOBuilder();
    }

    public MovieCreateRequestDTOBuilder withName(String movieName) {
        this.name = movieName;
        return this;
    }

    public MovieCreateRequestDTO build() {
        return new MovieCreateRequestDTO(name, yearProduced, rating);
    }
}
