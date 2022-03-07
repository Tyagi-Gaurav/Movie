package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.ContentStore;
import com.gt.scr.movie.service.domain.MovieStream;
import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ContentUploadServiceImpl implements ContentUploadService {
    private final ContentStore contentStore;

    public ContentUploadServiceImpl(ContentStore contentStore) {
        this.contentStore = contentStore;
    }

    @Override
    public Mono<MovieStreamMetaData> saveStream(MovieStream movieStream) {
        return contentStore.store(movieStream);
    }
}
