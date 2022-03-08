package com.gt.scr.movie.service.domain;

import java.util.UUID;

public record MovieStreamMetaData(UUID movieId,
                                  UUID streamId,
                                  String streamName,
                                  long sequence,
                                  long sizeInBytes,
                                  long creationTimeStamp) {
}
