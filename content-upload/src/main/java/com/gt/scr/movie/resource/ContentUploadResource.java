package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.ByteStreamUploadDTO;
import com.gt.scr.movie.resource.domain.ByteStreamUploadResponseDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.ContentUploadService;
import com.gt.scr.movie.service.domain.MovieStream;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class ContentUploadResource {
    private final SecurityContextHolder securityContextHolder;
    private final ContentUploadService contentUploadService;

    public ContentUploadResource(SecurityContextHolder securityContextHolder,
                                 ContentUploadService contentUploadService) {
        this.securityContextHolder = securityContextHolder;
        this.contentUploadService = contentUploadService;
    }

    @PostMapping(consumes = "application/vnd.movie.stream.add.v1+json",
            produces = "application/vnd.movie.stream.add.v1+json",
            path = "/user/movie/stream")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<ByteStreamUploadResponseDTO> uploadStream(@Valid @RequestBody ByteStreamUploadDTO byteStreamUploadDTO) {
        return securityContextHolder.getContext(UserProfile.class)
                .flatMap(up ->
                    contentUploadService.saveStream(new MovieStream(byteStreamUploadDTO.movieId(),
                            byteStreamUploadDTO.streamName(), byteStreamUploadDTO.byteStream()))
                )
                .map(movieStreamMetaData -> {
                    return new ByteStreamUploadResponseDTO(byteStreamUploadDTO.movieId(),
                            movieStreamMetaData.streamId(),
                            movieStreamMetaData.sequence(),
                            movieStreamMetaData.sizeInBytes(),
                            byteStreamUploadDTO.streamName());
                });
    }
}
