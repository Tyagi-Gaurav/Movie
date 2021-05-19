package com.toptal.scr.tz.service;

import com.toptal.scr.tz.service.domain.ImmutableUser;
import com.toptal.scr.tz.service.domain.User;
import com.toptal.scr.tz.service.domain.UserTimezone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        uuidUserTimezoneMap.put(timezone.id(), timezone);

        userService.update(ImmutableUser.builder().from(userFromDatabase)
                .userTimeZones(uuidUserTimezoneMap).build());
    }
}
