package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.ImmutableTimezoneDTO;
import com.gt.scr.movie.resource.domain.ImmutableTimezonesDTO;
import com.gt.scr.movie.resource.domain.TimezoneUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.TimezonesDTO;
import com.gt.scr.movie.resource.domain.TimezoneCreateRequestDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.TimezoneService;
import com.gt.scr.movie.service.domain.ImmutableUserTimezone;
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
public class TimezoneResource {
    private static final Logger LOG = LoggerFactory.getLogger(TimezoneResource.class);

    @Autowired
    private TimezoneService timezoneService;

    @PostMapping(consumes = "application/vnd.timezone.add.v1+json",
            produces = "application/vnd.timezone.add.v1+json")
    public ResponseEntity<Void> createTimezone(@RequestBody TimezoneCreateRequestDTO timezoneCreateRequestDTO,
                                               @RequestAttribute("userProfile") UserProfile userProfile,
                                               @RequestParam(value = "userId", required = false) String userId) {
        UserTimezone timezone = ImmutableUserTimezone.builder()
                .id(UUID.randomUUID())
                .city(timezoneCreateRequestDTO.city())
                .name(timezoneCreateRequestDTO.name())
                .gmtOffset(timezoneCreateRequestDTO.gmtOffset())
                .build();

        timezoneService.addTimezone(determineUserId(userId, userProfile.id()), timezone);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(consumes = "application/vnd.timezone.read.v1+json",
            produces = "application/vnd.timezone.read.v1+json")
    public ResponseEntity<TimezonesDTO> readTimezone(@RequestAttribute("userProfile") UserProfile userProfile,
                                                     @RequestParam(value = "userId", required = false) String userId) {

        Map<UUID, UserTimezone> timezones = timezoneService.getTimezones(determineUserId(userId, userProfile.id()));

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
    public ResponseEntity<Void> deleteTimezone(@RequestAttribute("userProfile") UserProfile userProfile,
                                               @RequestParam("id") String id,
                                               @RequestParam(value = "userId", required = false) String userId) {
        timezoneService.deleteTimezone(determineUserId(userId, userProfile.id()),
                UUID.fromString(id));

        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = "application/vnd.timezone.update.v1+json",
            produces = "application/vnd.timezone.update.v1+json")
    public ResponseEntity<Void> updateTimezone(@RequestBody TimezoneUpdateRequestDTO timezoneUpdateRequestDTO,
                                               @RequestAttribute("userProfile") UserProfile userProfile,
                                               @RequestParam(value = "userId", required = false) String userId) {

        UserTimezone timezone = ImmutableUserTimezone.builder()
                .id(timezoneUpdateRequestDTO.id())
                .city(timezoneUpdateRequestDTO.city())
                .name(timezoneUpdateRequestDTO.name())
                .gmtOffset(timezoneUpdateRequestDTO.gmtOffset())
                .build();

        timezoneService.updateTimezone(determineUserId(userId, userProfile.id()), timezone);

        return ResponseEntity.ok().build();
    }

    private UUID determineUserId(String userId, UUID id) {
        return userId == null ? id : UUID.fromString(userId);
    }
}
