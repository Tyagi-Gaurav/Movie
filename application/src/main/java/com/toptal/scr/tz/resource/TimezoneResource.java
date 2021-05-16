package com.toptal.scr.tz.resource;

import com.toptal.scr.tz.resource.domain.TimezoneCreateRequestDTO;
import com.toptal.scr.tz.resource.domain.UserProfile;
import com.toptal.scr.tz.service.TimezoneService;
import com.toptal.scr.tz.service.domain.ImmutableUserTimezone;
import com.toptal.scr.tz.service.domain.UserTimezone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TimezoneResource {
    private static final Logger LOG = LoggerFactory.getLogger(LoginResource.class);

    @Autowired
    private TimezoneService timezoneService;

    @PostMapping(path = "/timezone",
            consumes = "application/vnd.timezone.v1+json",
            produces = "application/vnd.timezone.v1+json")
    public ResponseEntity<Void> createTimezone(@RequestBody TimezoneCreateRequestDTO timezoneCreateRequestDTO,
                                               @RequestAttribute("authorities") UserProfile userProfile) {

        LOG.info("UserProfile: " + userProfile);

        UserTimezone timezone = ImmutableUserTimezone.builder()
                .id(UUID.randomUUID())
                .city(timezoneCreateRequestDTO.city())
                .name(timezoneCreateRequestDTO.name())
                .gmtOffset(timezoneCreateRequestDTO.gmtOffset())
                .build();

        timezoneService.addTimezone(userProfile.userName(), timezone);
        return ResponseEntity.noContent().build();
    }
}
