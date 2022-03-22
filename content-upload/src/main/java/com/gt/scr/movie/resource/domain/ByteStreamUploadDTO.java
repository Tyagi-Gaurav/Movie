package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.UUID;

import static java.util.Objects.isNull;

@JsonDeserialize
@JsonSerialize
public record ByteStreamUploadDTO(@NotNull UUID movieId,
                                  String streamName,
                                  @NotNull @NotEmpty byte[] byteStream) {

    @Override
    public byte[] byteStream() {
        if (!isNull(byteStream)) {
            return Arrays.copyOf(byteStream, byteStream.length);
        }

        return ArrayUtils.EMPTY_BYTE_ARRAY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ByteStreamUploadDTO that = (ByteStreamUploadDTO) o;

        return new EqualsBuilder().append(movieId, that.movieId).append(streamName, that.streamName).append(byteStream, that.byteStream).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(movieId).append(streamName).append(byteStream).toHashCode();
    }

    @Override
    public String toString() {
        return "ByteStreamUploadDTO{" +
                "movieId=" + movieId +
                ", streamName='" + streamName + '\'' +
                '}';
    }
}
