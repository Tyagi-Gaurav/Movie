package com.toptal.scr.tz.resource;

import com.toptal.scr.tz.resource.domain.ImmutableTimezoneDTO;
import com.toptal.scr.tz.resource.domain.ImmutableTimezonesDTO;
import com.toptal.scr.tz.resource.domain.TimezoneCreateRequestDTO;
import com.toptal.scr.tz.resource.domain.TimezonesDTO;
import com.toptal.scr.tz.resource.domain.UserProfile;
import com.toptal.scr.tz.service.TimezoneService;
import com.toptal.scr.tz.service.domain.ImmutableUserTimezone;
import com.toptal.scr.tz.service.domain.UserTimezone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class TimezoneResource {
    private static final Logger LOG = LoggerFactory.getLogger(LoginResource.class);

    @Autowired
    private TimezoneService timezoneService;

    @PostMapping(path = "/timezone",
            consumes = "application/vnd.timezone.v1+json",
            produces = "application/vnd.timezone.v1+json")
    public ResponseEntity<Void> createTimezone(@RequestBody TimezoneCreateRequestDTO timezoneCreateRequestDTO,
                                               @RequestAttribute("userProfile") UserProfile userProfile) {

        UserTimezone timezone = ImmutableUserTimezone.builder()
                .id(UUID.randomUUID())
                .city(timezoneCreateRequestDTO.city())
                .name(timezoneCreateRequestDTO.name())
                .gmtOffset(timezoneCreateRequestDTO.gmtOffset())
                .build();

        timezoneService.addTimezone(userProfile.userName(), timezone);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/timezone",
            consumes = "application/vnd.timezone.read.v1+json",
            produces = "application/vnd.timezone.read.v1+json")
    public ResponseEntity<TimezonesDTO> readTimezone(@RequestAttribute("userProfile") UserProfile userProfile) {
        Map<UUID, UserTimezone> timezones = timezoneService.getTimezones(userProfile.userName());

        List<ImmutableTimezoneDTO> timezoneDTOList = timezones.values().stream().map(tz -> ImmutableTimezoneDTO.builder()
                .city(tz.city())
                .id(tz.id())
                .gmtOffset(tz.gmtOffset())
                .name(tz.name())
                .build()).collect(Collectors.toList());

        TimezonesDTO timezonesDTO = ImmutableTimezonesDTO.builder().timezones(timezoneDTOList).build();

        return ResponseEntity.ok(timezonesDTO);
    }

    @DeleteMapping(path = "/timezone",
            consumes = "application/vnd.timezone.delete.v1+json",
            produces = "application/vnd.timezone.delete.v1+json")
    public ResponseEntity<Void> deleteTimezone(@RequestAttribute("userProfile") UserProfile userProfile,
                                               @RequestParam(name = "id") String id) {
        timezoneService.deleteTimezone(userProfile.userName(),
                UUID.fromString(id));

        return ResponseEntity.ok().build();
    }
}
