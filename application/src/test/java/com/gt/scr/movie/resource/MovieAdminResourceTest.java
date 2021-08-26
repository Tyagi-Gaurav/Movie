package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
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
        String content = TestUtils.asJsonString(new MovieCreateRequestDTO(randomAlphabetic(6),
                2010, BigDecimal.valueOf(10)));

        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "ADMIN");

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
        UserProfile userProfile = new UserProfile(usersOwnId, "ADMIN");

        Movie expectedReturnObject = ImmutableMovie.builder()
                .id(usersOwnId)
                .name(randomAlphabetic(5))
                .rating(BigDecimal.ONE)
                .yearProduced(2010)
                .build();

        when(movieService.getMoviesFor(requestedUserId)).thenReturn(List.of(expectedReturnObject));

        //when
        MvcResult mvcResult = mockMvc.perform(get("/user/movie")
                .requestAttr("userProfile", userProfile)
                .param("userId", requestedUserId.toString())
                .contentType("application/vnd.movie.read.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(movieService).getMoviesFor(requestedUserId);
        verify(movieService, times(0)).getMoviesFor(usersOwnId);
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
        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "ADMIN");
        UUID movieId = UUID.randomUUID();

        //when
        mockMvc.perform(delete("/user/movie")
                .requestAttr("userProfile", userProfile)
                .param("id", movieId.toString())
                .param("userId", requestedUserId.toString())
                .contentType("application/vnd.movie.delete.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(movieService).deleteMovie(movieId);
    }

    @Test
    void shouldAllowUserToUpdateMovies() throws Exception {
        UUID requestedUserId = UUID.randomUUID();
        String content = TestUtils.asJsonString(
                    new MovieUpdateRequestDTO(UUID.randomUUID(), randomAlphabetic(5),
                            BigDecimal.ZERO, 2010));

        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "USER");

        //when
        mockMvc.perform(put("/user/movie")
                .content(content)
                .param("userId", requestedUserId.toString())
                .requestAttr("userProfile", userProfile)
                .contentType("application/vnd.movie.update.v1+json"))
                .andExpect(status().isOk());

        //TODO
//        verify(movieService).updateMovie(eq(requestedUserId), any(Movie.class));
//        verify(movieService, times(0)).updateMovie(eq(userProfile.id()), any(Movie.class));
    }


}