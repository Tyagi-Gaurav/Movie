package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.ImmutableMovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableTimezoneUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableUserProfile;
import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.resource.domain.MoviesDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.MovieService;
import com.gt.scr.movie.service.domain.ImmutableMovie;
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
    void shouldAllowUserToCreateMovie() throws Exception {
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
    void shouldAllowUserToReadMovies() throws Exception {
        UUID id = UUID.randomUUID();
        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(id)
                .authority("USER")
                .build();

        Map<UUID, Movie> moviesMap = new HashMap<>();
        Movie expectedReturnObject = ImmutableMovie.builder()
                .id(id)
                .name(randomAlphabetic(5))
                .yearProduced(2010)
                .rating(BigDecimal.ONE)
                .build();
        moviesMap.put(userProfile.id(), expectedReturnObject);

        when(movieService.getMovieRating(id)).thenReturn(moviesMap);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/user/timezone")
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.movie.read.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(movieService).getMovieRating(eq(userProfile.id()));
        MoviesDTO moviesDTO = TestUtils.readFromString(mvcResult.getResponse().getContentAsString(),
                MoviesDTO.class);

        assertThat(moviesDTO.movies()).hasSize(1);
        MovieDTO actual = moviesDTO.movies().get(0);
        assertThat(actual.name()).isEqualTo(expectedReturnObject.name());
        assertThat(actual.rating()).isEqualTo(expectedReturnObject.rating());
        assertThat(actual.yearProduced()).isEqualTo(expectedReturnObject.yearProduced());
    }

    @Test
    void shouldAllowUserToDeleteMovies() throws Exception {
        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(UUID.randomUUID())
                .authority("USER")
                .build();
        UUID timezoneId = UUID.randomUUID();

        //when
        mockMvc.perform(delete("/user/timezone")
                .requestAttr("userProfile", userProfile)
                .param("id", timezoneId.toString())
                .contentType("application/vnd.movie.delete.v1+json"))
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