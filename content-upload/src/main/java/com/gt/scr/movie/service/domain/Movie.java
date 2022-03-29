package com.gt.scr.movie.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
public record Movie(UUID id,
                    String name,
                    int yearProduced,
                    BigDecimal rating,
                    Genre genre,
                    ContentType contentType,
                    AgeRating ageRating,
                    boolean isShareable,
                    long creationTimeStamp) implements Serializable  {}
