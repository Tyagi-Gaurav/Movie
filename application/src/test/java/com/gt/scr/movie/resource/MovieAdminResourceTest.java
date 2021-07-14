package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.ImmutableMovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableMovieUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableUserProfile;
import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.resource.domain.MoviesDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.MovieService;
import com.gt.scr.movie.service.domain.ImmutableMovie;
import com.gt.scr.movie.service.domain.Movie;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@EnableWebMvc
@SpringBootTest(classes = MovieResource.class)
class MovieAdminResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    void shouldAllowAdminToCreateMovies() throws Exception {
        UUID requestedUserId = UUID.randomUUID();
        String content = TestUtils.asJsonString(ImmutableMovieCreateRequestDTO.builder()
                .name(randomAlphabetic(6))
                .rating(BigDecimal.valueOf(10))
                .yearProduced(2010)
                .build());

        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(UUID.randomUUID())
                .authority("ADMIN")
                .build();

        //when
        mockMvc.perform(post("/user/movie")
                .content(content)
                .param("userId", requestedUserId.toString())
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.movie.add.v1+json"))
                .andExpect(status().isNoContent());

        verify(movieService).addMovie(eq(requestedUserId), any(Movie.class));
        verify(movieService, times(0)).addMovie(eq(userProfile.id()),
                any(Movie.class));
    }

    @Test
    void shouldAllowAdminToReadMovies() throws Exception {
        UUID requestedUserId = UUID.randomUUID();
        UUID usersOwnId = UUID.randomUUID();
        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(usersOwnId)
                .authority("ADMIN")
                .build();

        Map<UUID, Movie> movieMap = new HashMap<>();
        Movie expectedReturnObject = ImmutableMovie.builder()
                .id(usersOwnId)
                .name(randomAlphabetic(5))
                .rating(BigDecimal.ONE)
                .yearProduced(2010)
                .build();
        movieMap.put(userProfile.id(), expectedReturnObject);

        when(movieService.getMovie(requestedUserId)).thenReturn(movieMap);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/user/movie")
                .requestAttr("userProfile", userProfile)
                .param("userId", requestedUserId.toString())
                .contentType("application/vnd.movie.read.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(movieService).getMovie(requestedUserId);
        verify(movieService, times(0)).getMovie(usersOwnId);
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
        UUID requestedUserId = UUID.randomUUID();
        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(UUID.randomUUID())
                .authority("ADMIN")
                .build();
        UUID movieId = UUID.randomUUID();

        //when
        mockMvc.perform(delete("/user/movie")
                .requestAttr("userProfile", userProfile)
                .param("id", movieId.toString())
                .param("userId", requestedUserId.toString())
                .contentType("application/vnd.movie.delete.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(movieService).deleteMovie(requestedUserId, movieId);
    }

    @Test
    void shouldAllowUserToUpdateMovies() throws Exception {
        UUID requestedUserId = UUID.randomUUID();
        String content = TestUtils.asJsonString(ImmutableMovieUpdateRequestDTO.builder()
                .id(UUID.randomUUID())
                .name(randomAlphabetic(5))
                .rating(BigDecimal.ZERO)
                .yearProduced(2010)
                .build());

        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(UUID.randomUUID())
                .authority("USER")
                .build();

        //when
        mockMvc.perform(put("/user/movie")
                .content(content)
                .param("userId", requestedUserId.toString())
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.movie.update.v1+json"))
                .andExpect(status().isOk());

        verify(movieService).updateMovie(eq(requestedUserId), any(Movie.class));
        verify(movieService, times(0)).updateMovie(eq(userProfile.id()), any(Movie.class));
    }


}