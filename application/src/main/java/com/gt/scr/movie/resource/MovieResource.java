package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.resource.domain.MovieUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.MoviesDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.MovieService;
import com.gt.scr.movie.service.domain.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/user/movie")
public class MovieResource {
    private final MovieService movieService;
    private static final Logger LOG = LoggerFactory.getLogger(MovieResource.class);
    private final Mono<SecurityContext> securityContextMono = ReactiveSecurityContextHolder.getContext();

    public MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping(consumes = "application/vnd.movie.add.v1+json",
            produces = "application/vnd.movie.add.v1+json")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> createMovie(@Valid @RequestBody MovieCreateRequestDTO movieCreateRequestDTO,
                                  @RequestParam(value = "userId", required = false) String userId) {
        return securityContextMono
                .filter(ctx -> Objects.nonNull(ctx.getAuthentication()))
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new IllegalAccessException("UserProfile not found"))))
                .map(UserProfile.class::cast)
                .flatMap(up -> movieService.addMovie(determineUserId(userId, up.id()), new Movie(UUID.randomUUID(),
                        movieCreateRequestDTO.name(),
                        movieCreateRequestDTO.yearProduced(),
                        movieCreateRequestDTO.rating(),
                        System.nanoTime())))
                .thenEmpty(Mono.empty());
    }

    @GetMapping(consumes = "application/vnd.movie.read.v1+json",
            produces = "application/vnd.movie.read.v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<MoviesDTO> getMovie(@RequestParam(value = "userId", required = false) String userId) {
        LOG.info("Inside get resource with userId: {}", userId);
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new IllegalAccessException("UserProfile not found"))))
                .map(UserProfile.class::cast)
                .flatMap(up -> movieService.getMoviesFor(determineUserId(userId, up.id())).collectList())
                .map(movie -> movie.stream().map(mv -> new MovieDTO(mv.id(), mv.name(),
                        mv.yearProduced(), mv.rating())).toList())
                .map(MoviesDTO::new);
    }

    @DeleteMapping(consumes = "application/vnd.movie.delete.v1+json",
            produces = "application/vnd.movie.delete.v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<Void> deleteMovie(@RequestParam("id") String movieId) {
        return movieService.deleteMovie(UUID.fromString(movieId));
    }

    @PutMapping(consumes = "application/vnd.movie.update.v1+json",
            produces = "application/vnd.movie.update.v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<Void> updateMovie(@RequestBody MovieUpdateRequestDTO movieUpdateRequestDTO) {

        Movie movie = new Movie(movieUpdateRequestDTO.id(),
                movieUpdateRequestDTO.name(),
                movieUpdateRequestDTO.yearProduced(),
                movieUpdateRequestDTO.rating(),
                System.nanoTime());

        return movieService.updateMovie(movie);
    }

    private UUID determineUserId(String userId, UUID id) {
        return userId == null ? id : UUID.fromString(userId);
    }
}
