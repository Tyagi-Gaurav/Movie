package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.ByteStreamUploadDTO;
import com.gt.scr.movie.resource.domain.ByteStreamUploadResponseDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.ContentUploadService;
import com.gt.scr.movie.service.domain.MovieStream;
import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContentUploadResourceTest {
    @Mock
    private SecurityContextHolder securityContextHolder;
    @Mock
    private ContentUploadService contentUploadService;

    private ContentUploadResource contentUploadResource;

    @BeforeEach
    void setUp() {
        contentUploadResource = new ContentUploadResource(securityContextHolder, contentUploadService);
    }

    @Test
    void shouldAllowUserToUploadByteStreamForAMovie() {
        byte[] byteStream = {0, 1, 2, 3, 4};
        UUID movieId = UUID.randomUUID();
        ByteStreamUploadDTO byteStreamUploadDTO = new ByteStreamUploadDTO(movieId,
                "TestStreamName", byteStream);

        UUID userId = UUID.randomUUID();
        UserProfile userProfile = new UserProfile(userId, "USER", "token");
        when(securityContextHolder.getContext(UserProfile.class)).thenReturn(Mono.just(userProfile));
        UUID streamId = UUID.randomUUID();
        when(contentUploadService.saveStream(any(MovieStream.class)))
                .thenReturn(Mono.just(new MovieStreamMetaData(movieId, streamId, "TestStreamName",
                        1, byteStream.length, LocalDateTime.now())));

        //when
        Mono<ByteStreamUploadResponseDTO> movie = contentUploadResource.uploadStream(byteStreamUploadDTO);

        StepVerifier.create(movie)
                .consumeNextWith(byteStreamUploadResponseDTO -> {
                    assertThat(byteStreamUploadResponseDTO.size()).isEqualTo(byteStream.length);
                    assertThat(byteStreamUploadResponseDTO.sequence()).isEqualTo(1);
                    assertThat(byteStreamUploadResponseDTO.streamName()).isEqualTo("TestStreamName");
                    assertThat(byteStreamUploadResponseDTO.streamId()).isEqualTo(streamId);
                    assertThat(byteStreamUploadResponseDTO.movieId()).isEqualTo(movieId);
                })
                .verifyComplete();
    }

//    @Test
//    void nullByteStreamShouldResultInError() {
//        Movie movie = MovieBuilder.aMovie().build();
//        MovieStream movieStream = StreamBuilder.aStream()
//                .with(movie)
//                .withStream(null)
//                .build();
//
//        Mono<MovieStreamMetaData> movieStreamInputMono = contentUploadService.saveStream(movie, movieStream);
//
//        StepVerifier.create(movieStreamInputMono)
//                .consumeErrorWith(throwable -> {
//                }).verifyComplete();
//    }
//
//    @Test
//    void invalidByteStreamShouldResultInError() {
//    }
}