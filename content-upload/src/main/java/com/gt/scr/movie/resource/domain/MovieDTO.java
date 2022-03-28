package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gt.scr.movie.service.domain.AgeRating;
import com.gt.scr.movie.service.domain.ContentType;
import com.gt.scr.movie.service.domain.Genre;

import java.math.BigDecimal;
import java.util.UUID;

@JsonSerialize
@JsonDeserialize
public record MovieDTO(UUID id,
                       String name,
                       int yearProduced,
                       BigDecimal rating,
                       Genre genre,
                       ContentType contentType,
                       AgeRating ageRating,
                       boolean isShareable) {

    public MovieDTO(UUID id, String name, int yearProduced, BigDecimal rating) {
        this(id, name, yearProduced, rating, null, null, null, false);
    }
}
