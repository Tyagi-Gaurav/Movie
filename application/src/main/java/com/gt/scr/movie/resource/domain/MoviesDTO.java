package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableMoviesDTO.Builder.class)
public interface MoviesDTO {
    List<MovieDTO> movies();
}
