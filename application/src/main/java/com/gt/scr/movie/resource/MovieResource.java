package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.ImmutableMovieDTO;
import com.gt.scr.movie.resource.domain.ImmutableMoviesDTO;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.resource.domain.MovieUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.MoviesDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.MovieService;
import com.gt.scr.movie.service.domain.ImmutableMovie;
import com.gt.scr.movie.service.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/movie")
public class MovieResource {
    @Autowired
    private MovieService movieService;

    @PostMapping(consumes = "application/vnd.movie.add.v1+json",
            produces = "application/vnd.movie.add.v1+json")
    public ResponseEntity<Void> createMovie(@Valid @RequestBody MovieCreateRequestDTO movieCreateRequestDTO,
                                            @RequestAttribute("userProfile") UserProfile userProfile,
                                            @RequestParam(value = "userId", required = false) String userId) {
        Movie movie = ImmutableMovie.builder()
                .id(UUID.randomUUID())
                .name(movieCreateRequestDTO.name())
                .rating(movieCreateRequestDTO.rating())
                .yearProduced(movieCreateRequestDTO.yearProduced())
                .creationTimeStamp(System.nanoTime())
                .build();

        movieService.addMovie(determineUserId(userId, userProfile.id()), movie);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(consumes = "application/vnd.movie.read.v1+json",
            produces = "application/vnd.movie.read.v1+json")
    public ResponseEntity<MoviesDTO> getMovie(@RequestAttribute("userProfile") UserProfile userProfile,
                                              @RequestParam(value = "userId", required = false) String userId) {

        List<Movie> movies = movieService.getMoviesFor(determineUserId(userId, userProfile.id()));

        List<MovieDTO> movieDTOs = movies.stream().map(mv -> ImmutableMovieDTO.builder()
                .id(mv.id())
                .name(mv.name())
                .rating(mv.rating())
                .yearProduced(mv.yearProduced())
                .build()).collect(Collectors.toList());

        MoviesDTO moviesDTO = ImmutableMoviesDTO.builder().movies(movieDTOs).build();
        return ResponseEntity.ok(moviesDTO);
    }

    @DeleteMapping(consumes = "application/vnd.movie.delete.v1+json",
            produces = "application/vnd.movie.delete.v1+json")
    public ResponseEntity<Void> deleteMovie(@RequestParam("id") String id) {
        movieService.deleteMovie(UUID.fromString(id));

        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = "application/vnd.movie.update.v1+json",
            produces = "application/vnd.movie.update.v1+json")
    public ResponseEntity<Void> updateMovie(@RequestBody MovieUpdateRequestDTO movieUpdateRequestDTO) {

        Movie movie = ImmutableMovie.builder()
                .id(movieUpdateRequestDTO.id())
                .rating(movieUpdateRequestDTO.rating())
                .name(movieUpdateRequestDTO.name())
                .yearProduced(movieUpdateRequestDTO.yearProduced())
                .build();

        movieService.updateMovie(movie);

        return ResponseEntity.ok().build();
    }

    private UUID determineUserId(String userId, UUID id) {
        return userId == null ? id : UUID.fromString(userId);
    }
}
