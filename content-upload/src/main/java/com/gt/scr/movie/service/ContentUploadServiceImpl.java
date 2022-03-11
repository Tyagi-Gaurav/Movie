package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.ContentStore;
import com.gt.scr.movie.dao.StreamMetaDataRepository;
import com.gt.scr.movie.exception.ContentUploadException;
import com.gt.scr.movie.service.domain.MovieStream;
import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ContentUploadServiceImpl implements ContentUploadService {
    private final ContentStore contentStore;
    private final StreamMetaDataRepository streamMetaDataRepository;

    public ContentUploadServiceImpl(ContentStore contentStore,
                                    StreamMetaDataRepository streamMetaDataRepository) {
        this.contentStore = contentStore;
        this.streamMetaDataRepository = streamMetaDataRepository;
    }

    @Override
    public Mono<MovieStreamMetaData> saveStream(MovieStream movieStream) {
        return contentStore.store(movieStream)
                .flatMap(movieStreamMetaData -> streamMetaDataRepository.store(movieStreamMetaData)
                        .then(Mono.just(movieStreamMetaData))
                        .onErrorMap(throwable -> new ContentUploadException(throwable.getMessage(), throwable)))
                .onErrorMap(throwable -> new ContentUploadException(throwable.getMessage(), throwable));
    }
}
