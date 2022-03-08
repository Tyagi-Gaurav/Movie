package com.gt.scr.movie.util;

import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class MovieStreamMetaDataBuilder {

    private final UUID streamId = UUID.randomUUID();
    private UUID movieId = UUID.randomUUID();
    private final String streamName = RandomStringUtils.randomAlphabetic(6);

    private MovieStreamMetaDataBuilder() {}

    public static MovieStreamMetaDataBuilder aMovieStreamMetaDataBuilder() {
        return new MovieStreamMetaDataBuilder();
    }

    public MovieStreamMetaData build() {
        return new MovieStreamMetaData(this.movieId,
                this.streamId,
                this.streamName,
                1,
                20,
                System.nanoTime());
    }

    public MovieStreamMetaDataBuilder withMovieId(UUID movieId) {
        this.movieId = movieId;
        return this;
    }
}
