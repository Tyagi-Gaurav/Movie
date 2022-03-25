package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gt.scr.movie.service.domain.AgeRating;
import com.gt.scr.movie.service.domain.ContentType;
import com.gt.scr.movie.service.domain.Genre;
import com.gt.scr.validator.MaxLength;
import com.gt.scr.validator.MinLength;

import javax.validation.Valid;
import java.math.BigDecimal;

@JsonSerialize
@JsonDeserialize
@Valid
public record MovieCreateRequestDTO(@MinLength(6) @MaxLength(20) String name,
                                    int yearProduced,
                                    BigDecimal rating,
                                    Genre genre,
                                    ContentType contentType,
                                    AgeRating ageRating,
                                    boolean isShareable) {

    public MovieCreateRequestDTO(String name, int yearProduced, BigDecimal rating) {
        this(name, yearProduced, rating, null, null, null, false);
    }
}
