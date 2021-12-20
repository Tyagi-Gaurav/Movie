package com.gt.scr.movie.resource;

import com.gt.scr.movie.ext.user.CreateUserByAdminClient;
import com.gt.scr.movie.ext.user.UserCreateRequestDTO;
import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.AccountUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.UserDetailsResponse;
import com.gt.scr.movie.resource.domain.UserListResponseDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.UserService;
import com.gt.scr.movie.service.domain.User;
import com.gt.scr.movie.util.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagementResourceTest {
    @Mock
    private UserService userService;

    @Mock
    private SecurityContextHolder securityContextHolder;

    @Mock
    private CreateUserByAdminClient createUserByAdminClient;

    private UserManagementResource userManagementResource;

    @BeforeEach
    void setUp() {
        userManagementResource = new UserManagementResource(userService, securityContextHolder,
                createUserByAdminClient);
    }

    @Test
    void shouldAllowAdminToCreateUser() {
        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "ADMIN", "token");
        when(securityContextHolder.getContext(UserProfile.class)).thenReturn(Mono.just(userProfile));
        when(createUserByAdminClient.createUser(anyString(), any(UserCreateRequestDTO.class))).thenReturn(Mono.empty());
        AccountCreateRequestDTO requestDTO = new AccountCreateRequestDTO(
                randomAlphabetic(10),
                randomAlphabetic(10),
                "sdfsdfx",
                "bsdfsdf",
                "USER");

        //when
        Mono<Void> response = userManagementResource.createUser(requestDTO);

        StepVerifier.create(response)
                .verifyComplete();

        verify(createUserByAdminClient).createUser("token", new UserCreateRequestDTO(
                requestDTO.userName(), requestDTO.password(),
                requestDTO.firstName(), requestDTO.lastName(), requestDTO.role()
        ));
    }

    @Test
    void shouldAllowAdminToReadAllUsers() {
        //given
        User user = UserBuilder.aUser().build();
        given(userService.getAllUsers()).willReturn(Flux.just(user));

        //when
        Mono<UserListResponseDTO> userListResponseDTOMono = userManagementResource.listUsers();

        verify(userService).getAllUsers();
        StepVerifier.create(userListResponseDTOMono)
                .consumeNextWith(userListResponseDTO -> {
                    List<UserDetailsResponse> userDetailsResponses = userListResponseDTO.userDetails();
                    assertThat(userDetailsResponses).isNotEmpty();

                    UserDetailsResponse userDetailsResponse = userDetailsResponses.get(0);

                    assertThat(userDetailsResponse.userName()).isEqualTo(user.getUsername());
                    assertThat(userDetailsResponse.firstName()).isEqualTo(user.firstName());
                    assertThat(userDetailsResponse.lastName()).isEqualTo(user.lastName());
                    assertThat(userDetailsResponse.id()).isEqualTo(user.id());
                })
                .verifyComplete();
    }

    @Test
    void shouldAllowAdminToDeleteUser() {
        UUID userIdToDelete = UUID.randomUUID();

        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "ADMIN", "token");
        when(securityContextHolder.getContext(UserProfile.class)).thenReturn(Mono.just(userProfile));
        when(userService.deleteUser(userIdToDelete)).thenReturn(Mono.empty());

        //when
        Mono<Void> voidMono = userManagementResource.deleteUser(userIdToDelete);

        StepVerifier.create(voidMono).verifyComplete();

        verify(userService).deleteUser(userIdToDelete);
    }

    @Test
    void shouldNotAllowAdminToDeleteSelf() {
        UUID userIdToDelete = UUID.randomUUID();

        UserProfile userProfile = new UserProfile(userIdToDelete, "ADMIN", "token");
        when(securityContextHolder.getContext(UserProfile.class)).thenReturn(Mono.just(userProfile));

        Mono<Void> voidMono = userManagementResource.deleteUser(userIdToDelete);

        StepVerifier.create(voidMono).verifyComplete();

        verifyNoInteractions(userService);
    }

    @Test
    void shouldAllowAdminToUpdateAUser() {
        UUID userIdToUpdate = UUID.randomUUID();
        AccountUpdateRequestDTO requestDTO = new AccountUpdateRequestDTO(randomAlphabetic(10),
                randomAlphabetic(10), "x", "b", "user");

        //when
        userManagementResource.updateUser(userIdToUpdate, requestDTO);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

        User user = userArgumentCaptor.getValue();
        assertThat(user.id()).isEqualTo(userIdToUpdate);
    }


}