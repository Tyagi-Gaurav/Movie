package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.AccountUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.UserDetailsResponse;
import com.gt.scr.movie.resource.domain.UserListResponseDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.UserService;
import com.gt.scr.movie.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user/manage")
public class UserManagementResource {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping(consumes = "application/vnd.user.add.v1+json",
            produces = "application/vnd.user.add.v1+json")
    public ResponseEntity<Void> createUser(@RequestBody AccountCreateRequestDTO accountCreateRequestDTO) {
        User user = new User(UUID.randomUUID(),
                accountCreateRequestDTO.firstName(),
                accountCreateRequestDTO.lastName(),
                accountCreateRequestDTO.userName(),
                passwordEncoder.encode(accountCreateRequestDTO.password()),
                Collections.singletonList(new SimpleGrantedAuthority(accountCreateRequestDTO.role())));

        userService.add(user);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(consumes = "application/vnd.user.read.v1+json",
            produces = "application/vnd.user.read.v1+json")
    public ResponseEntity<UserListResponseDTO> listUsers() {
        List<User> allUsers = userService.getAllUsers();

        List<UserDetailsResponse> userDetailsResponses = allUsers.stream()
                .map(user -> new UserDetailsResponse(user.getUsername(), user.firstName(), user.lastName(),
                        user.getRole(), user.id())).toList();

        return ResponseEntity.ok(new UserListResponseDTO(userDetailsResponses));
    }

    @DeleteMapping(consumes = "application/vnd.user.delete.v1+json",
            produces = "application/vnd.user.delete.v1+json")
    public ResponseEntity<Void> deleteUser(@RequestParam("userId") UUID userId,
                                           @RequestAttribute("userProfile") UserProfile userProfile) {

        if (!userProfile.id().equals(userId)) {
            userService.deleteUser(userId);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = "application/vnd.user.update.v1+json",
            produces = "application/vnd.user.update.v1+json")
    public ResponseEntity<Void> updateUser(@RequestParam("userId") UUID userId,
                                           @RequestBody AccountUpdateRequestDTO accountUpdateRequestDTO) {

        User user = new User(userId,
                accountUpdateRequestDTO.firstName(),
                accountUpdateRequestDTO.lastName(),
                accountUpdateRequestDTO.userName(),
                passwordEncoder.encode(accountUpdateRequestDTO.password()),
                Collections.singletonList(new SimpleGrantedAuthority(accountUpdateRequestDTO.role())));

        userService.update(user);

        return ResponseEntity.ok().build();
    }
}
