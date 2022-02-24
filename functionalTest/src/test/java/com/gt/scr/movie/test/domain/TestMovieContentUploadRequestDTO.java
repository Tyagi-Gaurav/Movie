package com.gt.scr.movie.test.domain;

import java.util.UUID;

public record TestMovieContentUploadRequestDTO(UUID movieId,
                                               byte[] content) {
}
