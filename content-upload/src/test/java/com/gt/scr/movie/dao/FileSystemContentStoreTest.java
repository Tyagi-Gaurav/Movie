package com.gt.scr.movie.dao;

import com.gt.scr.movie.config.FileSystemContentStoreConfig;
import com.gt.scr.movie.exception.ContentUploadException;
import com.gt.scr.movie.service.domain.MovieStream;
import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.nio.file.Files.createTempDirectory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileSystemContentStoreTest {
    private ContentStore contentStore;
    private Path rootDirectory;
    private static final UUID streamId = UUID.randomUUID();

    @Mock
    private Supplier<UUID> streamIdSupplier;

    @BeforeEach
    void setUp() throws IOException {
        rootDirectory = createTempDirectory("tempPrefix",
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx")));
        when(streamIdSupplier.get()).thenReturn(streamId);
        FileSystemContentStoreConfig fileSystemContentStoreConfig = new FileSystemContentStoreConfig(rootDirectory.toAbsolutePath().toString());
        contentStore = new FileSystemContentStore(fileSystemContentStoreConfig, streamIdSupplier);
    }

    @Test
    void shouldStoreBytesOnFileSystemForANewMovie() {
        byte[] byteStream = {1, 2, 3, 4};
        UUID movieId = UUID.randomUUID();
        MovieStream movieStream = new MovieStream(movieId, "testStreamName", byteStream);

        Mono<MovieStreamMetaData> movieStreamMetaData = contentStore.store(movieStream);

        verifyStoredContent(byteStream, movieId, movieStreamMetaData, 1);
    }

    private void verifyStoredContent(byte[] byteStream,
                                     UUID movieId,
                                     Mono<MovieStreamMetaData> movieStreamMetaData,
                                     int sequence) {
        StepVerifier.create(movieStreamMetaData)
                .consumeNextWith(result -> {
                    assertThat(result.uploadDate()).isBetween(LocalDateTime.now().minus(3, ChronoUnit.SECONDS),
                            LocalDateTime.now());
                    assertThat(result.sequence()).isEqualTo(sequence);
                    assertThat(result.streamName()).isEqualTo("testStreamName");
                    assertThat(result.streamId()).isEqualTo(streamId);
                    assertThat(result.movieId()).isEqualTo(movieId);
                    assertThat(result.sizeInBytes()).isEqualTo(byteStream.length);
                    CharSequence moviePrefix = movieId.toString().subSequence(0, 6);
                    CharSequence streamPrefix = streamId.toString().subSequence(0, 6);
                    Path movieDirectory = rootDirectory.resolve(moviePrefix.toString());

                    String expectedFileName = String.format("%d_%s", result.sequence(), streamPrefix);

                    try {
                        Stream<Path> pathStream = Files.walk(movieDirectory.toAbsolutePath());
                        Path absoluteFilePath = movieDirectory.resolve(expectedFileName);
                        assertThat(pathStream.anyMatch(path -> !Files.isDirectory(path) &&
                                path.getFileName().toString().contains(expectedFileName)))
                                .describedAs("Unable to find %s in %s", expectedFileName, movieDirectory.toAbsolutePath())
                                .isTrue();
                        assertThat(Files.getPosixFilePermissions(absoluteFilePath))
                                .isEqualTo(PosixFilePermissions.fromString("rw-r--r--"));
                    } catch (IOException e) {
                        Assertions.fail(e.getMessage(), e);
                    }
                }).verifyComplete();
    }

    @Test
    void shouldReturnFailureIfFileSystemDoesNotHaveSufficientWritePermissions() throws IOException {
        rootDirectory = createTempDirectory("tempPrefix2",
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("---------")));
        FileSystemContentStoreConfig fileSystemContentStoreConfig = new FileSystemContentStoreConfig(rootDirectory.toAbsolutePath().toString());
        contentStore = new FileSystemContentStore(fileSystemContentStoreConfig, streamIdSupplier);

        byte[] byteStream = {1, 2, 3, 4};
        UUID movieId = UUID.randomUUID();
        MovieStream movieStream = new MovieStream(movieId, "testStreamName", byteStream);

        Mono<MovieStreamMetaData> movieStreamMetaData = contentStore.store(movieStream);

        StepVerifier.create(movieStreamMetaData)
                .consumeErrorWith(throwable -> {
                    assertThat(throwable).isInstanceOf(ContentUploadException.class);
                    assertThat(throwable.getCause()).isInstanceOf(AccessDeniedException.class);
                }).verify();
    }

    @Test
    void shouldAppendByteStreamToFileSystemForAnExistingMovie() {
        byte[] byteStream = {1, 2, 3, 4};
        UUID movieId = UUID.randomUUID();
        MovieStream movieStream = new MovieStream(movieId, "testStreamName", byteStream);

        Mono<MovieStreamMetaData> movieStreamMetaData1 = contentStore.store(movieStream); //Store 1st
        Mono<MovieStreamMetaData> movieStreamMetaData2 = contentStore.store(movieStream); //Store 2nd

        verifyStoredContent(byteStream, movieId, movieStreamMetaData1, 1);
        verifyStoredContent(byteStream, movieId, movieStreamMetaData2, 2);
    }
}
