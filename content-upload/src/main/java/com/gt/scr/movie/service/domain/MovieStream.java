package com.gt.scr.movie.service.domain;

import java.util.UUID;

public record MovieStream(UUID movieId, String streamName, byte[] byteStream) {
}
