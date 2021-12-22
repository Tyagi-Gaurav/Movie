package com.gt.scr.user.resource;

import com.gt.scr.spc.domain.User;
import com.gt.scr.user.resource.domain.UserDetailsResponseDTO;
import com.gt.scr.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserResource {
    private final UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(consumes = "application/vnd.user.fetchByUserName.v1+json",
            produces = "application/vnd.user.fetchByUserName.v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<UserDetailsResponseDTO> getByName(@RequestParam String userName) {
        return userService.findByUsername(userName)
                .map(User.class::cast)
                .map(user -> new UserDetailsResponseDTO(user.username(),
                        user.password(), user.firstName(), user.lastName(),
                        user.getRole(), user.id()))
                .switchIfEmpty(Mono.defer(() -> {
                    LOG.info("Unable to find user in user database for: {}", userName);
                    return Mono.empty();
                }));
    }

    @GetMapping(consumes = "application/vnd.user.fetchByUserId.v1+json",
            produces = "application/vnd.user.fetchByUserId.v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<UserDetailsResponseDTO> getById(@RequestParam String userId) {
        return userService.findUserBy(UUID.fromString(userId))
                .map(user -> new UserDetailsResponseDTO(user.username(),
                        user.password(), user.firstName(), user.lastName(),
                        user.getRole(), user.id()))
                .switchIfEmpty(Mono.defer(() -> {
                    LOG.info("Unable to find user in user database for: {}", userId);
                    return Mono.empty();
                }));
    }

    @DeleteMapping(consumes = "application/vnd.user.deleteUser.v1+json",
            produces = "application/vnd.user.deleteUser.v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<Void> deleteUserBy(@RequestParam String userId) {
        return userService.deleteUser(UUID.fromString(userId));
    }
}
