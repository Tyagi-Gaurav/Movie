package com.toptal.scr.tz.service;

import com.toptal.scr.tz.service.domain.ImmutableUser;
import com.toptal.scr.tz.service.domain.ImmutableUserTimezone;
import com.toptal.scr.tz.service.domain.User;
import com.toptal.scr.tz.service.domain.UserTimezone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class TimezoneServiceImpl implements TimezoneService {

    @Autowired
    private UserService userService;

    @Override
    public void addTimezone(UUID userId, UserTimezone userTimezone) {
        User userFromDatabase = userService.findUserBy(userId);
        HashMap<UUID, UserTimezone> uuidUserTimezoneHashMap =
                userFromDatabase.userTimeZones();

        uuidUserTimezoneHashMap.put(userTimezone.id(), userTimezone);

        userService.update(ImmutableUser.builder().from(userFromDatabase)
                .userTimeZones(uuidUserTimezoneHashMap).build());
    }

    @Override
    public Map<UUID, UserTimezone> getTimezones(UUID userId) {
        User userFromDatabase = userService.findUserBy(userId);
        return userFromDatabase.userTimeZones();
    }

    @Override
    public void deleteTimezone(UUID userId, UUID timezoneId) {
        User userFromDatabase = userService.findUserBy(userId);

        HashMap<UUID, UserTimezone> uuidUserTimezoneMap = userFromDatabase.userTimeZones();
        uuidUserTimezoneMap.remove(timezoneId);

        userService.update(ImmutableUser.builder().from(userFromDatabase)
                .userTimeZones(uuidUserTimezoneMap).build());
    }

    @Override
    public void updateTimezone(UUID userId, UserTimezone timezone) {
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
