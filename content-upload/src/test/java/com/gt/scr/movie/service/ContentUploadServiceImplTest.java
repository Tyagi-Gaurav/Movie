package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.ContentStore;
import com.gt.scr.movie.dao.StreamMetaDataRepository;
import com.gt.scr.movie.exception.ContentUploadException;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
        long uploadTime = System.nanoTime();

        when(streamMetaDataRepository.store(any(MovieStreamMetaData.class)))
                .thenReturn(Mono.empty());

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

    @Test
    void handleExceptionFromStoringByteStreamOnDisk() {
        Movie movie = MovieBuilder.aMovie().build();
        MovieStream movieStream = StreamBuilder.aStream().withMovieId(movie.id()).build();

        when(contentStore.store(movieStream)).thenReturn(Mono.error(new IOException()));

        Mono<MovieStreamMetaData> movieStreamInputMono = contentUploadService.saveStream(movieStream);

        StepVerifier.create(movieStreamInputMono)
                .consumeErrorWith(throwable ->
                        assertThat(throwable).isInstanceOf(ContentUploadException.class)
                ).verify();
    }

    @Test
    void handleExceptionFromStoringByteStreamToDatabase() {
        Movie movie = MovieBuilder.aMovie().build();
        MovieStream movieStream = StreamBuilder.aStream().withMovieId(movie.id()).build();
        UUID streamId = UUID.randomUUID();
        long uploadTime = System.nanoTime();

        MovieStreamMetaData movieStreamMetaData = new MovieStreamMetaData(movie.id(), streamId,
                movieStream.streamName(), 1, 5L, uploadTime);
        when(contentStore.store(movieStream))
                .thenReturn(Mono.just(movieStreamMetaData));
        when(streamMetaDataRepository.store(movieStreamMetaData)).thenReturn(Mono.error(new SQLException()));

        Mono<MovieStreamMetaData> movieStreamInputMono = contentUploadService.saveStream(movieStream);

        StepVerifier.create(movieStreamInputMono)
                .consumeErrorWith(throwable ->
                        assertThat(throwable).isInstanceOf(ContentUploadException.class)
                ).verify();
    }
}
