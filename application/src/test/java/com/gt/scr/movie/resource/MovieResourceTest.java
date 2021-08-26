package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.resource.domain.MovieUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.MoviesDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.MovieService;
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
import java.util.List;
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
        String content = TestUtils.asJsonString(
                new MovieCreateRequestDTO(randomAlphabetic(6),
                        2010, BigDecimal.ONE));

        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "USER");

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
        UserProfile userProfile = new UserProfile(id, "USER");

        var expectedReturnObject = new Movie(id, randomAlphabetic(5),
                2010, BigDecimal.ONE, System.nanoTime());

        when(movieService.getMoviesFor(id)).thenReturn(List.of(expectedReturnObject));

        //when
        MvcResult mvcResult = mockMvc.perform(get("/user/movie")
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.movie.read.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(movieService).getMoviesFor(userProfile.id());
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
        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "USER");
        UUID movieId = UUID.randomUUID();

        //when
        mockMvc.perform(delete("/user/movie")
                .requestAttr("userProfile", userProfile)
                .param("id", movieId.toString())
                .contentType("application/vnd.movie.delete.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(movieService).deleteMovie(movieId);
    }

    @Test
    void shouldAllowUserToUpdateMovies() throws Exception {
        String content = TestUtils.asJsonString(new MovieUpdateRequestDTO(UUID.randomUUID(),
                randomAlphabetic(5), BigDecimal.ZERO, 2010));

        var userProfile = new UserProfile(UUID.randomUUID(), "USER");

        //when
        mockMvc.perform(put("/user/movie")
                .content(content)
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.movie.update.v1+json"))
                .andExpect(status().isOk());

        verify(movieService).updateMovie(any(Movie.class));
    }


}