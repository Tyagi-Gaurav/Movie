package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.ImmutableTimezoneDTO;
import com.gt.scr.movie.resource.domain.ImmutableTimezonesDTO;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.TimezoneUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.TimezonesDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.MovieService;
import com.gt.scr.movie.service.domain.ImmutableMovie;
import com.gt.scr.movie.service.domain.ImmutableUserTimezone;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.UserTimezone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/timezone")
public class MovieResource {
    private static final Logger LOG = LoggerFactory.getLogger(MovieResource.class);

    @Autowired
    private MovieService movieService;

    @PostMapping(consumes = "application/vnd.movie.add.v1+json",
            produces = "application/vnd.movie.add.v1+json")
    public ResponseEntity<Void> createMovieRating(@RequestBody MovieCreateRequestDTO movieCreateRequestDTO,
                                                  @RequestAttribute("userProfile") UserProfile userProfile,
                                                  @RequestParam(value = "userId", required = false) String userId) {
        Movie movie = ImmutableMovie.builder()
                .id(UUID.randomUUID())
                .name(movieCreateRequestDTO.name())
                .rating(movieCreateRequestDTO.rating())
                .yearProduced(movieCreateRequestDTO.yearProduced())
                .build();

        movieService.addMovieRating(determineUserId(userId, userProfile.id()), movie);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(consumes = "application/vnd.timezone.read.v1+json",
            produces = "application/vnd.timezone.read.v1+json")
    public ResponseEntity<TimezonesDTO> getMovieRating(@RequestAttribute("userProfile") UserProfile userProfile,
                                                       @RequestParam(value = "userId", required = false) String userId) {

        Map<UUID, UserTimezone> timezones = movieService.getMovieRating(determineUserId(userId, userProfile.id()));

        List<ImmutableTimezoneDTO> timezoneDTOList = timezones.values().stream().map(tz -> ImmutableTimezoneDTO.builder()
                .city(tz.city())
                .id(tz.id())
                .gmtOffset(tz.gmtOffset())
                .name(tz.name())
                .build()).collect(Collectors.toList());

        TimezonesDTO timezonesDTO = ImmutableTimezonesDTO.builder().timezones(timezoneDTOList).build();
        return ResponseEntity.ok(timezonesDTO);
    }

    @DeleteMapping(consumes = "application/vnd.timezone.delete.v1+json",
            produces = "application/vnd.timezone.delete.v1+json")
    public ResponseEntity<Void> deleteMovieRating(@RequestAttribute("userProfile") UserProfile userProfile,
                                                  @RequestParam("id") String id,
                                                  @RequestParam(value = "userId", required = false) String userId) {
        movieService.deleteMovieRating(determineUserId(userId, userProfile.id()),
                UUID.fromString(id));

        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = "application/vnd.timezone.update.v1+json",
            produces = "application/vnd.timezone.update.v1+json")
    public ResponseEntity<Void> updateMovieRating(@RequestBody TimezoneUpdateRequestDTO timezoneUpdateRequestDTO,
                                                  @RequestAttribute("userProfile") UserProfile userProfile,
                                                  @RequestParam(value = "userId", required = false) String userId) {

        UserTimezone timezone = ImmutableUserTimezone.builder()
                .id(timezoneUpdateRequestDTO.id())
                .city(timezoneUpdateRequestDTO.city())
                .name(timezoneUpdateRequestDTO.name())
                .gmtOffset(timezoneUpdateRequestDTO.gmtOffset())
                .build();

        movieService.updateMovieRating(determineUserId(userId, userProfile.id()), timezone);

        return ResponseEntity.ok().build();
    }

    private UUID determineUserId(String userId, UUID id) {
        return userId == null ? id : UUID.fromString(userId);
    }
}
