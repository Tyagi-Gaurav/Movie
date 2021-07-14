package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.ImmutableMovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableMovieUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableMovieVideoUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableUserProfile;
import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.resource.domain.MovieUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.MoviesDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.MovieService;
import com.gt.scr.movie.service.domain.ImmutableMovie;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
        mockMvc.perform(post("/user/movie")
                .content(content)
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.movie.add.v1+json"))
                .andExpect(status().isNoContent());

        verify(movieService).addMovie(eq(userProfile.id()), any(Movie.class));
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

        when(movieService.getMovie(id)).thenReturn(moviesMap);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/user/movie")
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.movie.read.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(movieService).getMovie(userProfile.id());
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
        UUID movieId = UUID.randomUUID();

        //when
        mockMvc.perform(delete("/user/movie")
                .requestAttr("userProfile", userProfile)
                .param("id", movieId.toString())
                .contentType("application/vnd.movie.delete.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(movieService).deleteMovie(userProfile.id(), movieId);
    }

    @Test
    void shouldAllowUserToUpdateMovies() throws Exception {
        MovieUpdateRequestDTO body = ImmutableMovieUpdateRequestDTO.builder()
                .id(UUID.randomUUID())
                .name(randomAlphabetic(5))
                .rating(BigDecimal.ZERO)
                .yearProduced(2010)
                .build();
        String content = TestUtils.asJsonString(body);

        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(UUID.randomUUID())
                .authority("USER")
                .build();

        //when
        mockMvc.perform(put("/user/movie")
                .content(content)
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.movie.update.v1+json"))
                .andExpect(status().isOk());

        ArgumentCaptor<Movie> movieArgumentCaptor = ArgumentCaptor.forClass(Movie.class);
        verify(movieService).updateMovie(eq(userProfile.id()), movieArgumentCaptor.capture());
        Movie movie = movieArgumentCaptor.getValue();

        assertThat(movie.id()).isEqualTo(body.id());
        assertThat(movie.name()).isEqualTo(body.name());
        assertThat(movie.rating()).isEqualTo(body.rating());
        assertThat(movie.yearProduced()).isEqualTo(body.yearProduced());
    }

    @Test
    void shouldAllowUserToUpdateMovieVideos() throws Exception {
        byte[] videoContents = {(byte) 0x81, (byte) 0xb6};
        MovieUpdateRequestDTO body = ImmutableMovieUpdateRequestDTO.builder()
                .id(UUID.randomUUID())
                .name(randomAlphabetic(5))
                .rating(BigDecimal.ZERO)
                .yearProduced(2010)
                .videoRequestDto(ImmutableMovieVideoUpdateRequestDTO.builder()
                        .content(videoContents)
                        .build())
                .build();
        String content = TestUtils.asJsonString(body);

        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(UUID.randomUUID())
                .authority("USER")
                .build();

        //when
        mockMvc.perform(put("/user/movie")
                .content(content)
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.movie.update.v1+json"))
                .andExpect(status().isOk());

        ArgumentCaptor<Movie> movieArgumentCaptor = ArgumentCaptor.forClass(Movie.class);
        verify(movieService).updateMovie(eq(userProfile.id()), movieArgumentCaptor.capture());
        Movie movie = movieArgumentCaptor.getValue();

        assertThat(movie.movieVideo()).isNotEmpty();
        assertThat(movie.movieVideo().get().content()).isEqualTo(videoContents);
    }


}