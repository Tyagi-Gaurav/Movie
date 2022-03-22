package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Collections;
import java.util.List;

@JsonSerialize
@JsonDeserialize
public record MoviesDTO(List<MovieDTO> movies) {

    @Override
    public List<MovieDTO> movies() {
        return Collections.unmodifiableList(movies);
    }
}
