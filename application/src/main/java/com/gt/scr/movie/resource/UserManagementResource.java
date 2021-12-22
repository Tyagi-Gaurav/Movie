package com.gt.scr.movie.resource;

import com.gt.scr.domain.User;
import com.gt.scr.movie.ext.user.CreateUserByAdminClient;
import com.gt.scr.movie.ext.user.UserCreateRequestDTO;
import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.AccountUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.UserDetailsResponse;
import com.gt.scr.movie.resource.domain.UserListResponseDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/user/manage")
public class UserManagementResource {
    private final UserService userService;
    private final SecurityContextHolder securityContextHolder;
    private final CreateUserByAdminClient createUserByAdminClient;

    public UserManagementResource(UserService userService,
                                  SecurityContextHolder securityContextHolder,
                                  CreateUserByAdminClient createUserByAdminClient) {
        this.userService = userService;
        this.securityContextHolder = securityContextHolder;
        this.createUserByAdminClient = createUserByAdminClient;
    }

    @PostMapping(consumes = "application/vnd.user.add.v1+json",
            produces = "application/vnd.user.add.v1+json")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> createUser(@RequestBody AccountCreateRequestDTO accountCreateRequestDTO) {
        return securityContextHolder.getContext(UserProfile.class)
                .flatMap(up ->
                        createUserByAdminClient.createUser(up.token(), new UserCreateRequestDTO(
                                accountCreateRequestDTO.userName(), accountCreateRequestDTO.password(),
                                accountCreateRequestDTO.firstName(), accountCreateRequestDTO.lastName(),
                                accountCreateRequestDTO.role()))
                );
    }

    @GetMapping(consumes = "application/vnd.user.read.v1+json",
            produces = "application/vnd.user.read.v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<UserListResponseDTO> listUsers() {
        return userService.getAllUsers()
                .map(user -> new UserDetailsResponse(user.getUsername(), user.firstName(), user.lastName(),
                        user.getRole(), user.id()))
                .collectList()
                .map(UserListResponseDTO::new);
    }

    @DeleteMapping(consumes = "application/vnd.user.delete.v1+json",
            produces = "application/vnd.user.delete.v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<Void> deleteUser(@RequestParam("userId") UUID userId) {
        return securityContextHolder.getContext(UserProfile.class)
                .filter(up -> !up.id().equals(userId))
                .flatMap(up -> userService.deleteUser(userId));
    }

    @PutMapping(consumes = "application/vnd.user.update.v1+json",
            produces = "application/vnd.user.update.v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<Void> updateUser(@RequestParam("userId") UUID userId,
                                 @RequestBody AccountUpdateRequestDTO accountUpdateRequestDTO) {

        User user = new User(userId,
                accountUpdateRequestDTO.firstName(),
                accountUpdateRequestDTO.lastName(),
                accountUpdateRequestDTO.userName(),
                accountUpdateRequestDTO.password(),
                Collections.singletonList(new SimpleGrantedAuthority(accountUpdateRequestDTO.role())));

        userService.update(user);

        return Mono.empty();
    }
}
