package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.ContentStore;
import com.gt.scr.movie.dao.StreamMetaDataRepository;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.MovieStream;
import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import com.gt.scr.movie.util.MovieBuilder;
import com.gt.scr.movie.util.StreamBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContentUploadServiceImplTest {
    private ContentUploadService contentUploadService;

    @Mock
    private ContentStore contentStore;

    @Mock
    private StreamMetaDataRepository streamMetaDataRepository;

    @BeforeEach
    void setUp() {
        contentUploadService = new ContentUploadServiceImpl(contentStore, streamMetaDataRepository);
    }

    @Test
    void shouldSaveContentStream() {
        Movie movie = MovieBuilder.aMovie().build();
        MovieStream movieStream = StreamBuilder.aStream().withMovieId(movie.id()).build();

        UUID streamId = UUID.randomUUID();
        Long uploadTime = System.nanoTime();

        when(contentStore.store(movieStream))
                .thenReturn(Mono.just(new MovieStreamMetaData(movie.id(), streamId,
                        movieStream.streamName(), 1, 5L, uploadTime)));

        Mono<MovieStreamMetaData> movieStreamInputMono = contentUploadService.saveStream(movieStream);

        StepVerifier.create(movieStreamInputMono)
                .consumeNextWith(movieStreamMetaData -> {
                    assertThat(movieStreamMetaData.movieId()).isEqualTo(movie.id());
                    assertThat(movieStreamMetaData.streamId()).isEqualTo(streamId);
                    assertThat(movieStreamMetaData.sizeInBytes()).isEqualTo(5);
                    assertThat(movieStreamMetaData.streamName()).isEqualTo(movieStream.streamName());
                    assertThat(movieStreamMetaData.sequence()).isEqualTo(1);
                    assertThat(movieStreamMetaData.creationTimeStamp()).isEqualTo(uploadTime);

                    verify(streamMetaDataRepository).store(movieStreamMetaData);
                }).verifyComplete();
    }
}
