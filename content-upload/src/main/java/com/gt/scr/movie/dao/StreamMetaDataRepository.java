package com.gt.scr.movie.dao;

import com.gt.scr.movie.service.domain.MovieStreamMetaData;

public interface StreamMetaDataRepository {
    void store(MovieStreamMetaData movieStreamMetaData);
}
