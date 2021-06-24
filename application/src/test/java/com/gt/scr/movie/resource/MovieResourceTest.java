package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.ImmutableMovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableTimezoneUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableUserProfile;
import com.gt.scr.movie.resource.domain.TimezoneDTO;
import com.gt.scr.movie.resource.domain.TimezonesDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.MovieService;
import com.gt.scr.movie.service.domain.ImmutableUserTimezone;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.UserTimezone;
import com.gt.scr.movie.util.TestUtils;
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@EnableWebMvc
@SpringBootTest(classes = MovieResource.class)
class MovieResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    void shouldAllowUserToCreateTimeZones() throws Exception {
        String content = TestUtils.asJsonString(ImmutableMovieCreateRequestDTO.builder()
                .name(randomAlphabetic(6))
                .rating(BigDecimal.ONE)
                .yearProduced(2010).build());

        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(UUID.randomUUID())
                .authority("USER")
                .build();

        //when
        mockMvc.perform(post("/user/timezone")
                .content(content)
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.movie.add.v1+json"))
                .andExpect(status().isNoContent());

        verify(movieService).addMovieRating(eq(userProfile.id()), any(Movie.class));
    }

    @Test
    void shouldAllowUserToReadTimeZones() throws Exception {
        UUID id = UUID.randomUUID();
        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(id)
                .authority("USER")
                .build();

        Map<UUID, UserTimezone> timezoneMap = new HashMap<>();
        ImmutableUserTimezone expectedReturnObject = ImmutableUserTimezone.builder()
                .id(id)
                .gmtOffset(1).name(randomAlphabetic(5)).city(randomAlphabetic(5))
                .build();
        timezoneMap.put(userProfile.id(), expectedReturnObject);

        when(movieService.getMovieRating(id)).thenReturn(timezoneMap);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/user/timezone")
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.timezone.read.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(movieService).getMovieRating(eq(userProfile.id()));
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
        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(UUID.randomUUID())
                .authority("USER")
                .build();
        UUID timezoneId = UUID.randomUUID();

        //when
        mockMvc.perform(delete("/user/timezone")
                .requestAttr("userProfile", userProfile)
                .param("id", timezoneId.toString())
                .contentType("application/vnd.timezone.delete.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(movieService).deleteMovieRating(userProfile.id(), timezoneId);
    }

    @Test
    void shouldAllowUserToUpdateTimeZones() throws Exception {
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
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.timezone.update.v1+json"))
                .andExpect(status().isOk());

        verify(movieService).updateMovieRating(eq(userProfile.id()), any(UserTimezone.class));
    }


}