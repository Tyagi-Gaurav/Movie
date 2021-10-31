package com.gt.scr.movie.grpc_resource;

import com.gt.scr.movie.grpc.AccountCreateGrpcRequestDTO;
import com.gt.scr.movie.grpc.CreateAccountServiceGrpc;
import com.gt.scr.movie.grpc.Empty;
import com.gt.scr.movie.service.UserService;
import com.gt.scr.movie.service.domain.User;
import io.grpc.stub.StreamObserver;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.UUID;

public class CreateAccountGrpcResource extends CreateAccountServiceGrpc.CreateAccountServiceImplBase {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CreateAccountGrpcResource(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createAccount(AccountCreateGrpcRequestDTO accountCreateRequestDTO,
                              StreamObserver<Empty> responseObserver) {
        User user = new User(UUID.randomUUID(),
                accountCreateRequestDTO.getFirstName(),
                accountCreateRequestDTO.getLastName(),
                accountCreateRequestDTO.getUserName(),
                passwordEncoder.encode(accountCreateRequestDTO.getPassword()),
                Collections.singletonList(new SimpleGrantedAuthority(accountCreateRequestDTO.getRole())));

        userService.add(user).block();
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
