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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class MovieResource {
    private final MovieService movieService;
    private static final Logger LOG = LoggerFactory.getLogger(MovieResource.class);
    private final SecurityContextHolder securityContextHolder;

    public MovieResource(MovieService movieService,
                         SecurityContextHolder securityContextHolder) {
        this.movieService = movieService;
        this.securityContextHolder = securityContextHolder;
    }

    @PostMapping(consumes = "application/vnd.movie.add.v1+json",
            produces = "application/vnd.movie.add.v1+json",
            path = "/user/movie")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> createMovie(@Valid @RequestBody MovieCreateRequestDTO movieCreateRequestDTO) {
        return securityContextHolder.getContext(UserProfile.class)
                .flatMap(up -> movieService.addMovie(up.id(), new Movie(UUID.randomUUID(),
                        movieCreateRequestDTO.name(),
                        movieCreateRequestDTO.yearProduced(),
                        movieCreateRequestDTO.rating(),
                        System.nanoTime())))
                .thenEmpty(Mono.empty());
    }

    @PostMapping(consumes = "application/vnd.movie.add.v1+json",
            produces = "application/vnd.movie.add.v1+json",
            path = "/user/{userId}/movie")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> createMovieFor(@Valid @RequestBody MovieCreateRequestDTO movieCreateRequestDTO,
                                     @PathVariable(value = "userId") String userId) {
        return securityContextHolder.getContext(UserProfile.class)
                .flatMap(up -> movieService.addMovie(UUID.fromString(userId), new Movie(UUID.randomUUID(),
                        movieCreateRequestDTO.name(),
                        movieCreateRequestDTO.yearProduced(),
                        movieCreateRequestDTO.rating(),
                        System.nanoTime())))
                .thenEmpty(Mono.empty());
    }

    @GetMapping(consumes = "application/vnd.movie.read.v1+json",
            produces = "application/vnd.movie.read.v1+json",
            path = "/user/movie")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<MoviesDTO> getMovie() {
        LOG.info("Inside get resource with userId");
        return securityContextHolder.getContext(UserProfile.class)
                .flatMap(up -> movieService.getMoviesFor(up.id()).collectList())
                .map(movie -> movie.stream().map(mv -> new MovieDTO(mv.id(), mv.name(),
                        mv.yearProduced(), mv.rating())).toList())
                .map(MoviesDTO::new);
    }

    @DeleteMapping(consumes = "application/vnd.movie.delete.v1+json",
            produces = "application/vnd.movie.delete.v1+json",
            path = "/user/movie")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<Void> deleteMovie(@RequestParam("id") String movieId) {
        return movieService.deleteMovie(UUID.fromString(movieId));
    }

    @PutMapping(consumes = "application/vnd.movie.update.v1+json",
            produces = "application/vnd.movie.update.v1+json",
            path = "/user/movie")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<Void> updateMovie(@RequestBody MovieUpdateRequestDTO movieUpdateRequestDTO) {

        Movie movie = new Movie(movieUpdateRequestDTO.id(),
                movieUpdateRequestDTO.name(),
                movieUpdateRequestDTO.yearProduced(),
                movieUpdateRequestDTO.rating(),
                System.nanoTime());

        return movieService.updateMovie(movie);
    }

    @GetMapping(consumes = "application/vnd.movie.read.v1+json",
            produces = "application/vnd.movie.read.v1+json",
            path = "/user/{userId}/movie")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<MoviesDTO> getMovieForUser(@PathVariable("userId") String userId) {
        return securityContextHolder.getContext(UserProfile.class)
                .flatMap(up -> movieService.getMoviesFor(UUID.fromString(userId)).collectList())
                .map(movie -> movie.stream().map(mv -> new MovieDTO(mv.id(), mv.name(),
                        mv.yearProduced(), mv.rating())).toList())
                .map(MoviesDTO::new);
    }
}
