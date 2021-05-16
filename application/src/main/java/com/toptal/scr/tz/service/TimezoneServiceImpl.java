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
    public void addTimezone(String user, UserTimezone userTimezone) {
        User userFromDatabase = userService.loadUserByUsername(user);
        HashMap<UUID, UserTimezone> uuidUserTimezoneHashMap =
                userFromDatabase.userTimeZones();

        uuidUserTimezoneHashMap.put(userTimezone.id(), userTimezone);

        userService.add(ImmutableUser.builder().from(userFromDatabase)
                .userTimeZones(uuidUserTimezoneHashMap).build());
    }

    @Override
    public Map<UUID, UserTimezone> getTimezones(String user) {
        User userFromDatabase = userService.loadUserByUsername(user);
        return userFromDatabase.userTimeZones();
    }

    @Override
    public void deleteTimezone(String user, UUID id) {
        User userFromDatabase = userService.loadUserByUsername(user);

        HashMap<UUID, UserTimezone> uuidUserTimezoneMap = userFromDatabase.userTimeZones();
        uuidUserTimezoneMap.remove(id);

        userService.add(ImmutableUser.builder().from(userFromDatabase)
                .userTimeZones(uuidUserTimezoneMap).build());
    }
}
