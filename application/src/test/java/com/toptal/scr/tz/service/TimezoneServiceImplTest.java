package com.toptal.scr.tz.service;

import com.toptal.scr.tz.service.domain.ImmutableUserTimezone;
import com.toptal.scr.tz.service.domain.User;
import com.toptal.scr.tz.service.domain.UserTimezone;
import com.toptal.scr.tz.util.TestBuilders;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TimezoneServiceImpl.class)
class TimezoneServiceImplTest {

    @Autowired
    private TimezoneService timezoneService;

    @MockBean
    private UserService userService;

    @Test
    void shouldAddTimezone() {
        //given
        UUID userId = UUID.randomUUID();
        UserTimezone userTimezone = TestBuilders.aUserTimezone();
        User user = TestBuilders.aUser();

        when(userService.findUserBy(userId)).thenReturn(user);

        //when
        timezoneService.addTimezone(userId, userTimezone);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

        User actualParameter = userArgumentCaptor.getValue();
        assertThat(actualParameter.userTimeZones()).contains(Map.entry(userTimezone.id(), userTimezone));
    }

    @Test
    void shouldRetrieveTimezone() {
        //given
        UUID userId = UUID.randomUUID();
        User user = TestBuilders.aUserWithTimezones();

        when(userService.findUserBy(userId)).thenReturn(user);

        //when
        Map<UUID, UserTimezone> timezones = timezoneService.getTimezones(userId);

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
        timezoneService.deleteTimezone(userId, userTimezone.id());

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
        timezoneService.updateTimezone(userId, userTimezone);

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
        timezoneService.updateTimezone(userId, updatedTimezone);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

        User actualParameter = userArgumentCaptor.getValue();
        assertThat(actualParameter.userTimeZones()).contains(Map.entry(timezoneId, expectedTimezone));
    }
}