package com.gt.scr.movie.util;

import com.gt.scr.movie.service.domain.MovieStream;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class StreamBuilder {

    private UUID movieId;
    private final String streamName = RandomStringUtils.randomAlphabetic(6);
    private final byte[] byteStream = RandomStringUtils.randomAlphabetic(20).getBytes(StandardCharsets.UTF_8);

    private StreamBuilder() {}

    public static StreamBuilder aStream() {
        return new StreamBuilder();
    }

    public MovieStream build() {
        return new MovieStream(movieId,
                streamName, byteStream);
    }

    public StreamBuilder withMovieId(UUID movieId) {
        this.movieId = movieId;
        return this;
    }
}
