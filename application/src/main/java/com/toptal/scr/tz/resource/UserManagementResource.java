package com.toptal.scr.tz.resource;

import com.toptal.scr.tz.resource.domain.AccountCreateRequestDTO;
import com.toptal.scr.tz.resource.domain.AccountUpdateRequestDTO;
import com.toptal.scr.tz.resource.domain.ImmutableUserDetailsResponse;
import com.toptal.scr.tz.resource.domain.ImmutableUserListResponseDTO;
import com.toptal.scr.tz.resource.domain.UserListResponseDTO;
import com.toptal.scr.tz.resource.domain.UserProfile;
import com.toptal.scr.tz.service.UserService;
import com.toptal.scr.tz.service.domain.ImmutableUser;
import com.toptal.scr.tz.service.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(UserManagementResource.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping(consumes = "application/vnd.user.add.v1+json",
            produces = "application/vnd.user.add.v1+json")
    public ResponseEntity<Void> createUser(@RequestBody AccountCreateRequestDTO accountCreateRequestDTO) {
        User user = ImmutableUser.builder()
                .id(UUID.randomUUID())
                .username(accountCreateRequestDTO.userName())
                .password(passwordEncoder.encode(accountCreateRequestDTO.password()))
                .firstName(accountCreateRequestDTO.firstName())
                .lastName(accountCreateRequestDTO.lastName())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(accountCreateRequestDTO.role())))
                .build();

        userService.add(user);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(consumes = "application/vnd.user.read.v1+json",
            produces = "application/vnd.user.read.v1+json")
    public ResponseEntity<UserListResponseDTO> listUsers() {
        List<User> allUsers = userService.getAllUsers();

        ImmutableUserListResponseDTO.Builder builder = ImmutableUserListResponseDTO.builder();
        allUsers.forEach(user -> builder.addUserDetails(ImmutableUserDetailsResponse.builder()
                .firstName(user.firstName())
                .lastName(user.lastName())
                .role(user.getRole())
                .userName(user.getUsername())
                .id(user.id())
                .build()));

        return ResponseEntity.ok(builder.build());
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

        User user = ImmutableUser.builder()
                .id(userId)
                .username(accountUpdateRequestDTO.userName())
                .password(passwordEncoder.encode(accountUpdateRequestDTO.password()))
                .firstName(accountUpdateRequestDTO.firstName())
                .lastName(accountUpdateRequestDTO.lastName())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(accountUpdateRequestDTO.role())))
                .build();

        userService.update(user);

        return ResponseEntity.ok().build();
    }
}
