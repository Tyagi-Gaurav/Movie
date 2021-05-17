package com.toptal.scr.tz.resource;

import com.toptal.scr.tz.resource.domain.ImmutableTimezoneCreateRequestDTO;
import com.toptal.scr.tz.resource.domain.ImmutableTimezoneUpdateRequestDTO;
import com.toptal.scr.tz.resource.domain.ImmutableUserProfile;
import com.toptal.scr.tz.resource.domain.TimezoneDTO;
import com.toptal.scr.tz.resource.domain.TimezonesDTO;
import com.toptal.scr.tz.resource.domain.UserProfile;
import com.toptal.scr.tz.service.TimezoneService;
import com.toptal.scr.tz.service.domain.ImmutableUserTimezone;
import com.toptal.scr.tz.service.domain.UserTimezone;
import com.toptal.scr.tz.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@EnableWebMvc
@SpringBootTest(classes = TimezoneResource.class)
class TimezoneAdminResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimezoneService timezoneService;

    @Test
    void shouldAllowAdminToCreateTimeZones() throws Exception {
        UUID requestedUserId = UUID.randomUUID();
        String content = TestUtils.asJsonString(ImmutableTimezoneCreateRequestDTO.builder()
                .city(randomAlphabetic(5))
                .name(randomAlphabetic(5))
                .gmtOffset(1).build());

        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(UUID.randomUUID())
                .authority("ADMIN")
                .build();

        //when
        mockMvc.perform(post("/user/timezone")
                .content(content)
                .param("userId", requestedUserId.toString())
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.timezone.add.v1+json"))
                .andExpect(status().isNoContent());

        verify(timezoneService).addTimezone(eq(requestedUserId), any(UserTimezone.class));
        verify(timezoneService, times(0)).addTimezone(eq(userProfile.id()),
                any(UserTimezone.class));
    }

    @Test
    void shouldAllowAdminToReadTimeZones() throws Exception {
        UUID requestedUserId = UUID.randomUUID();
        UUID usersOwnId = UUID.randomUUID();
        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(usersOwnId)
                .authority("ADMIN")
                .build();

        Map<UUID, UserTimezone> timezoneMap = new HashMap<>();
        ImmutableUserTimezone expectedReturnObject = ImmutableUserTimezone.builder()
                .id(usersOwnId)
                .gmtOffset(1).name(randomAlphabetic(5)).city(randomAlphabetic(5))
                .build();
        timezoneMap.put(userProfile.id(), expectedReturnObject);

        when(timezoneService.getTimezones(requestedUserId)).thenReturn(timezoneMap);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/user/timezone")
                .requestAttr("userProfile", userProfile)
                .param("userId", requestedUserId.toString())
                .contentType("application/vnd.timezone.read.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(timezoneService).getTimezones(requestedUserId);
        verify(timezoneService, times(0)).getTimezones(usersOwnId);
        TimezonesDTO timezonesDTO = TestUtils.readFromString(mvcResult.getResponse().getContentAsString(),
                TimezonesDTO.class);

        assertThat(timezonesDTO.timezones()).hasSize(1);
        TimezoneDTO actual = timezonesDTO.timezones().get(0);
        assertThat(actual.city()).isEqualTo(expectedReturnObject.city());
        assertThat(actual.name()).isEqualTo(expectedReturnObject.name());
        assertThat(actual.gmtOffset()).isEqualTo(expectedReturnObject.gmtOffset());
    }

    @Test
    void shouldAllowUserToDeleteTimeZones() throws Exception {
        UUID requestedUserId = UUID.randomUUID();
        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(UUID.randomUUID())
                .authority("ADMIN")
                .build();
        UUID timezoneId = UUID.randomUUID();

        //when
        MvcResult mvcResult = mockMvc.perform(delete("/user/timezone")
                .requestAttr("userProfile", userProfile)
                .param("id", timezoneId.toString())
                .param("userId", requestedUserId.toString())
                .contentType("application/vnd.timezone.delete.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(timezoneService).deleteTimezone(requestedUserId, timezoneId);
    }

    @Test
    void shouldAllowUserToUpdateTimeZones() throws Exception {
        UUID requestedUserId = UUID.randomUUID();
        String content = TestUtils.asJsonString(ImmutableTimezoneUpdateRequestDTO.builder()
                .id(UUID.randomUUID())
                .city(randomAlphabetic(5))
                .name(randomAlphabetic(5))
                .gmtOffset(1).build());

        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(UUID.randomUUID())
                .authority("USER")
                .build();

        //when
        mockMvc.perform(put("/user/timezone")
                .content(content)
                .param("userId", requestedUserId.toString())
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.timezone.update.v1+json"))
                .andExpect(status().isOk());

        verify(timezoneService).updateTimezone(eq(requestedUserId), any(UserTimezone.class));
        verify(timezoneService, times(0)).updateTimezone(eq(userProfile.id()), any(UserTimezone.class));
    }


}