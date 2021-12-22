package com.gt.scr.user.resource;

import com.gt.scr.spc.domain.User;
import com.gt.scr.user.resource.domain.UserDetailsResponseDTO;
import com.gt.scr.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static com.gt.scr.user.util.UserBuilder.aUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserResourceTest {
    private UserResource userResource;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        userResource = new UserResource(userService);
    }

    @Test
    void shouldRetrieveUserByName() {
        //given
        User testUser = aUser().build();
        when(userService.findByUsername(testUser.getUsername())).thenReturn(Mono.just(testUser));

        //when
        Mono<UserDetailsResponseDTO> userResponse = userResource.getByName(testUser.getUsername());

        //then
        StepVerifier.create(userResponse)
                .expectNext(new UserDetailsResponseDTO(
                        testUser.username(), testUser.password(),
                        testUser.firstName(), testUser.lastName(), testUser.getRole(),
                        testUser.id()
                ))
                .verifyComplete();

        verify(userService).findByUsername(testUser.getUsername());
    }

    @Test
    void shouldReturnEmptyWhenNoUserFound() {
        //given
        when(userService.findByUsername(anyString())).thenReturn(Mono.empty());

        //when
        Mono<UserDetailsResponseDTO> userResponse = userResource.getByName("user");

        //then
        StepVerifier.create(userResponse)
                .verifyComplete();

        verify(userService).findByUsername("user");
    }

    @Test
    void shouldRetrieveUserById() {
        //given
        User testUser = aUser().build();
        UUID uuid = UUID.randomUUID();
        when(userService.findUserBy(uuid)).thenReturn(Mono.just(testUser));

        //when
        Mono<UserDetailsResponseDTO> userResponse = userResource.getById(uuid.toString());

        //then
        StepVerifier.create(userResponse)
                .expectNext(new UserDetailsResponseDTO(
                        testUser.username(), testUser.password(),
                        testUser.firstName(), testUser.lastName(), testUser.getRole(),
                        testUser.id()
                ))
                .verifyComplete();

        verify(userService).findUserBy(uuid);
    }

    @Test
    void shouldDeleteUserById() {
        //given
        UUID uuid = UUID.randomUUID();
        when(userService.deleteUser(uuid)).thenReturn(Mono.empty());

        //when
        Mono<Void> userResponse = userResource.deleteUserBy(uuid.toString());

        //then
        StepVerifier.create(userResponse)
                .expectComplete();

        verify(userService).deleteUser(uuid);
    }

    @Test
    void shouldReturnEmptyWhenNoUserFoundById() {
        //given
        when(userService.findUserBy(any())).thenReturn(Mono.empty());

        //when
        Mono<UserDetailsResponseDTO> userResponse = userResource.getById(UUID.randomUUID().toString());

        //then
        StepVerifier.create(userResponse)
                .verifyComplete();

        verify(userService).findUserBy(any(UUID.class));
    }
}
