package com.gt.scr.movie.service;

import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.ImmutableUser;
import com.gt.scr.movie.service.domain.ImmutableUserTimezone;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.User;
import com.gt.scr.movie.service.domain.UserTimezone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private UserService userService;

    @Override
    public void addMovieRating(UUID userId, Movie movie) {
        User userFromDatabase = userService.findUserBy(userId);
        HashMap<UUID, Movie> uuidUserMoviesHashMap =
                userFromDatabase.movies();

        boolean alreadyExists = uuidUserMoviesHashMap.values()
                .stream().anyMatch(tz ->
                        tz.name().equalsIgnoreCase(movie.name()) &&
                        tz.yearProduced() == movie.yearProduced());

        if (alreadyExists) {
            throw new DuplicateRecordException("Movie already exists");
        }

        uuidUserMoviesHashMap.put(movie.id(), movie);

        userService.update(ImmutableUser.builder().from(userFromDatabase)
                .movies(uuidUserMoviesHashMap).build());
    }

    @Override
    public Map<UUID, Movie> getMovieRating(UUID userId) {
        User userFromDatabase = userService.findUserBy(userId);
        return userFromDatabase.movies();
    }

    @Override
    public void deleteMovieRating(UUID userId, UUID timezoneId) {
        User userFromDatabase = userService.findUserBy(userId);

        HashMap<UUID, UserTimezone> uuidUserTimezoneMap = userFromDatabase.userTimeZones();
        uuidUserTimezoneMap.remove(timezoneId);

        userService.update(ImmutableUser.builder().from(userFromDatabase)
                .userTimeZones(uuidUserTimezoneMap).build());
    }

    @Override
    public void updateMovieRating(UUID userId, UserTimezone timezone) {
        User userFromDatabase = userService.findUserBy(userId);

        HashMap<UUID, UserTimezone> uuidUserTimezoneMap = userFromDatabase.userTimeZones();

        uuidUserTimezoneMap.computeIfPresent(timezone.id(), (uuid, userTimezone) -> ImmutableUserTimezone.builder()
                .name(isNotBlank(timezone.name()) ? timezone.name() : userTimezone.name())
                .city(isNotBlank(timezone.city()) ? timezone.city() : userTimezone.city())
                .gmtOffset(timezone.gmtOffset() != -100 ? timezone.gmtOffset() : userTimezone.gmtOffset())
                .id(timezone.id())
                .build());

        userService.update(ImmutableUser.builder().from(userFromDatabase)
                .userTimeZones(uuidUserTimezoneMap).build());
    }
}
