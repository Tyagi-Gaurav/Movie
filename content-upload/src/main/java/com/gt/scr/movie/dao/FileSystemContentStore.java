package com.gt.scr.movie.dao;

import com.gt.scr.movie.config.FileSystemContentStoreConfig;
import com.gt.scr.movie.exception.ContentUploadException;
import com.gt.scr.movie.service.domain.MovieStream;
import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Component
public class FileSystemContentStore implements ContentStore {
    private static final Logger LOG = LoggerFactory.getLogger(FileSystemContentStore.class);
    private final FileSystemContentStoreConfig fileSystemContentStoreConfig;
    private final Supplier<UUID> streamIdSupplier;

    public FileSystemContentStore(FileSystemContentStoreConfig fileSystemContentStoreConfig,
                                  @Qualifier("fileContentStoreUUIDProvider") Supplier<UUID> streamIdSupplier) {
        this.fileSystemContentStoreConfig = fileSystemContentStoreConfig;
        this.streamIdSupplier = streamIdSupplier;
    }

    @Override
    public Mono<MovieStreamMetaData> store(MovieStream movieStream) {
        return writeToFile(movieStream)
                .onErrorMap(throwable -> new ContentUploadException(throwable.getMessage(), throwable));
    }

    private Mono<MovieStreamMetaData> writeToFile(MovieStream movieStream) {
        UUID streamId = streamIdSupplier.get();
        CharSequence moviePrefix = movieStream.movieId().toString().subSequence(0, 6);
        String movieDirectory = fileSystemContentStoreConfig.contentStoreRoot() + "/" + moviePrefix;
        CharSequence streamPrefix = streamId.toString().subSequence(0, 6);

        Path movieDirectoryPath = Path.of(new File(movieDirectory).toURI());

        try {
            Files.createDirectories(movieDirectoryPath, PosixFilePermissions.asFileAttribute(
                    PosixFilePermissions.fromString("rwx------")
            ));

            long nextSequence = getNextSequence(movieDirectoryPath);
            String fileName = String.format("%d_%s", nextSequence, streamPrefix);
            Path absoluteFileStreamPath = movieDirectoryPath.resolve(fileName);
            Files.write(absoluteFileStreamPath, movieStream.byteStream());
            return Mono.just(new MovieStreamMetaData(movieStream.movieId(), streamId, movieStream.streamName(),
                    nextSequence, movieStream.byteStream().length, System.nanoTime()));

        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return Mono.error(e);
        }
    }

    private long getNextSequence(Path movieDirectoryPath) throws IOException {
        try(Stream<Path> fileStream = Files.walk(movieDirectoryPath)) {
            return 1 + fileStream.map(Path::toFile)
                    .filter(f -> !f.isDirectory() && !f.isHidden())
                    .count();
        }
    }

}
