package com.gt.scr.movie.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = ImmutableMovieVideo.Builder.class)
public interface MovieVideo extends Serializable {
    byte[] content();
}
