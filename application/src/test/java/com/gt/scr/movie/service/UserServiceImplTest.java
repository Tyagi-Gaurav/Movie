package com.gt.scr.movie.service;

import com.gt.scr.exception.DuplicateRecordException;
import com.gt.scr.movie.dao.UserRepository;
import com.gt.scr.movie.ext.user.CreateUserClient;
import com.gt.scr.movie.ext.user.DeleteUsersClient;
import com.gt.scr.movie.ext.user.FetchUsersByIdClient;
import com.gt.scr.movie.ext.user.FetchUsersByNameClient;
import com.gt.scr.movie.ext.user.ListUsersClient;
import com.gt.scr.movie.ext.user.UserCreateRequestDTO;
import com.gt.scr.movie.ext.user.UserDetailsResponseDTO;
import com.gt.scr.movie.ext.user.UserListResponseDTO;
import com.gt.scr.movie.resource.SecurityContextHolder;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static com.gt.scr.movie.util.UserBuilder.aUser;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserService userService;

    @Mock
    private UserRepository repository;

    @Mock
    private CreateUserClient createUserClient;

    @Mock
    private ListUsersClient listUsersClient;

    @Mock
    private FetchUsersByNameClient fetchUsersByNameClient;

    @Mock
    private FetchUsersByIdClient fetchUsersByIdClient;

    @Mock
    private SecurityContextHolder securityContextHolder;

    @Mock
    private DeleteUsersClient deleteUsersClient;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(createUserClient,
                listUsersClient,
                fetchUsersByNameClient,
                fetchUsersByIdClient,
                deleteUsersClient,
                securityContextHolder,
                repository);
    }

    @Test
    void shouldAddUser() {
        User user = aUser().build();
        when(createUserClient.createUser(any())).thenReturn(Mono.empty());

        //when
        Mono<Void> add = userService.add(user);

        //then
        StepVerifier.create(add).verifyComplete();
        verifyNoInteractions(repository);
        verify(createUserClient).createUser(new UserCreateRequestDTO(
                user.username(), user.password(),
                user.firstName(), user.lastName(),
                user.getRole()
        ));
    }

    @Test
    void shouldDeleteUser() {
        UUID userId = UUID.randomUUID();
        String userToken = "token";
        when(securityContextHolder.getContext(UserProfile.class))
                .thenReturn(Mono.just(new UserProfile(UUID.randomUUID(), "ADMIN", userToken)));
        when(deleteUsersClient.deleteUser(userId.toString(), userToken)).thenReturn(Mono.empty());

        //when
        Mono<Void> deleteMono = userService.deleteUser(userId);

        //then
        StepVerifier.create(deleteMono).verifyComplete();
        verifyNoInteractions(repository);
    }

    @Test
    void shouldFindUserByUserName() {
        String userName = "userName";
        User user = aUser().withUserName(userName).build();
        when(fetchUsersByNameClient.fetchUserBy(userName)).thenReturn(Mono.just(
                new UserDetailsResponseDTO(
                        user.username(),
                        user.password(),
                        user.firstName(), user.lastName(), user.getRole(),
                        user.id()
                )
        ));

        //when
        Mono<UserDetails> byUsername = userService.findByUsername(userName);

        //then
        StepVerifier.create(byUsername)
                .expectNext(user)
                .expectComplete();
        verifyNoInteractions(repository);
        verify(fetchUsersByNameClient).fetchUserBy(userName);
    }

    @Test
    void shouldRetrieveListOfAllUsers() {
        User user = aUser().withUserPassword("").build();
        String token = RandomStringUtils.randomAlphabetic(20);
        when(securityContextHolder.getContext(UserProfile.class))
                .thenReturn(Mono.just(new UserProfile(UUID.randomUUID(), "ADMIN", token)));
        when(listUsersClient.listAllUsers(token))
                .thenReturn(Mono.just(new UserListResponseDTO(
                        List.of(new UserDetailsResponseDTO(
                                user.username(),
                                user.password(),
                                user.firstName(),
                                user.lastName(),
                                user.getRole(),
                                user.id()
                        )))));

        //when
        Flux<User> allUsers = userService.getAllUsers();

        //then
        StepVerifier.create(allUsers)
                .expectNext(user)
                .verifyComplete();

        verify(securityContextHolder).getContext(UserProfile.class);
        verify(listUsersClient).listAllUsers(token);
    }


    @Test
    void shouldReturnErrorWhenNoUserFoundByName() {
        String userName = "userName";
        when(fetchUsersByNameClient.fetchUserBy(userName)).thenReturn(Mono.empty());

        //when
        Mono<UserDetails> byUsername = userService.findByUsername(userName);

        //then
        StepVerifier.create(byUsername)
                .expectError(UsernameNotFoundException.class)
                .verify();
        verifyNoInteractions(repository);
        verify(fetchUsersByNameClient).fetchUserBy(userName);
    }

    @Test
    void shouldFindUserByUserId() {
        UUID userId = UUID.randomUUID();
        User user = aUser().build();
        when(fetchUsersByIdClient.fetchUserBy(userId)).thenReturn(Mono.just(
                new UserDetailsResponseDTO(
                        user.username(),
                        user.password(),
                        user.firstName(), user.lastName(), user.getRole(),
                        userId
                )
        ));

        //when
        userService.findUserBy(userId);

        //then
        verifyNoInteractions(repository);
        verify(fetchUsersByIdClient).fetchUserBy(userId);
    }

    @Test
    void shouldUpdateUser() {
        User user = aUser().build();

        //when
        Mono<Void> update = userService.update(user);

        //then
        StepVerifier.create(update).verifyComplete();
        verify(repository).update(user);
    }

    @Disabled
    void shouldThrowExceptionWhenTryingToAddDuplicateUser() {
        //given
        User userA = aUser().build();
        User userB = aUser().withUserName(userA.getUsername()).build();
        when(repository.findUserBy(userA.getUsername())).thenReturn(Mono.empty()).thenReturn(Mono.just(userA));
        userService.add(userA);

        //when
        Mono<Void> add = userService.add(userB);

        //then
        StepVerifier.create(add)
                .expectError(DuplicateRecordException.class);
        verify(repository, times(2)).findUserBy(userA.getUsername());
    }
}