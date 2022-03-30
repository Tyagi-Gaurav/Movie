package com.gt.scr.movie.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AgeRating {
    U("U"),
    PG("PG"),
    TWELVE_A("12A"),
    FIFTEEN("15"),
    EIGHTEEN("18");

    private final String ageRatingValue;

    AgeRating(String ageRatingValue) {
        this.ageRatingValue = ageRatingValue;
    }

    @JsonValue
    public String getAgeRatingValue() {
        return ageRatingValue;
    }
}
