package com.gt.scr.movie.service.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

public record MovieStream(UUID movieId, String streamName, byte[] byteStream) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MovieStream that = (MovieStream) o;

        return new EqualsBuilder().append(movieId, that.movieId).append(streamName, that.streamName).append(byteStream, that.byteStream).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(movieId).append(streamName).append(byteStream).toHashCode();
    }

    @Override
    public String toString() {
        return "MovieStream{" +
                "movieId=" + movieId +
                ", streamName='" + streamName + '\'' +
                '}';
    }
}
