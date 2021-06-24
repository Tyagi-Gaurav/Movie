package com.gt.scr.movie.service;

import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.ImmutableUserTimezone;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.User;
import com.gt.scr.movie.service.domain.UserTimezone;
import com.gt.scr.movie.util.TestBuilders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = MovieServiceImpl.class)
class MovieServiceImplTest {

    @Autowired
    private MovieService movieService;

    @MockBean
    private UserService userService;

    @Test
    void shouldAddTimezone() {
        //given
        UUID userId = UUID.randomUUID();
        Movie movie = TestBuilders.aUserMovie();
        User user = TestBuilders.aUser();

        when(userService.findUserBy(userId)).thenReturn(user);

        //when
        movieService.addMovieRating(userId, movie);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

        User actualParameter = userArgumentCaptor.getValue();
        assertThat(actualParameter.userMovies()).contains(Map.entry(movie.id(), movie));
    }

    @Test
    void shouldNotAllowAddingDuplicateTimezoneWithAllValuesSame() {
        //given
        UUID userId = UUID.randomUUID();
        Movie movie = TestBuilders.aUserMovie();
        User user = TestBuilders.aUser();

        when(userService.findUserBy(userId)).thenReturn(user);
        movieService.addMovieRating(userId, movie);

        //when
        Throwable throwable = catchThrowable(() -> movieService.addMovieRating(userId, movie));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(DuplicateRecordException.class);
    }

    @Test
    void shouldRetrieveTimezone() {
        //given
        UUID userId = UUID.randomUUID();
        User user = TestBuilders.aUserWithTimezones();

        when(userService.findUserBy(userId)).thenReturn(user);

        //when
        Map<UUID, UserTimezone> timezones = movieService.getMovieRating(userId);

        //then
        assertThat(timezones).isEqualTo(user.userTimeZones());
    }

    @Test
    void shouldDeleteTimezoneForAUser() {
        //given
        UUID userId = UUID.randomUUID();
        UserTimezone userTimezone = TestBuilders.aUserTimezone();
        User user = TestBuilders.aUser();

        when(userService.findUserBy(userId)).thenReturn(user);

        //when
        movieService.deleteMovieRating(userId, userTimezone.id());

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

        User actualParameter = userArgumentCaptor.getValue();
        assertThat(actualParameter.userTimeZones()).doesNotContain(Map.entry(userTimezone.id(), userTimezone));
    }

    @Test
    void shouldUpdateTimezoneForAUser() {
        //given
        UUID userId = UUID.randomUUID();
        UserTimezone userTimezone = TestBuilders.aUserTimezone();
        User user = TestBuilders.aUser();

        when(userService.findUserBy(userId)).thenReturn(user);

        //when
        movieService.updateMovieRating(userId, userTimezone);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

        User actualParameter = userArgumentCaptor.getValue();
        assertThat(actualParameter.userTimeZones()).isEmpty();
    }

    @Test
    void shouldUpdateOnlyTimezoneNameForAUser() {
        //given
        UUID userId = UUID.randomUUID();
        UUID timezoneId = UUID.randomUUID();
        UserTimezone timezoneToUpdate = TestBuilders.aUserTimezone();
        HashMap<UUID, UserTimezone> timezoneMap = new HashMap<>();

        timezoneMap.put(timezoneId, timezoneToUpdate);
        User user = TestBuilders.aUserWithTimezones(timezoneMap);

        when(userService.findUserBy(userId)).thenReturn(user);
        String newTimezoneName = "newName";
        UserTimezone expectedTimezone = ImmutableUserTimezone.builder()
                .name(newTimezoneName)
                .id(timezoneId)
                .city(timezoneToUpdate.city())
                .gmtOffset(timezoneToUpdate.gmtOffset())
                .build();

        //when
        UserTimezone updatedTimezone = ImmutableUserTimezone.builder()
                .name(newTimezoneName)
                .city("")
                .gmtOffset(-100)
                .id(timezoneId)
                .build();
        movieService.updateMovieRating(userId, updatedTimezone);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

        User actualParameter = userArgumentCaptor.getValue();
        assertThat(actualParameter.userTimeZones()).contains(Map.entry(timezoneId, expectedTimezone));
    }
}