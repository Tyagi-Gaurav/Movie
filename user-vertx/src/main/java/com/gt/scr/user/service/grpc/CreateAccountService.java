package com.gt.scr.user.service.grpc;

import com.google.protobuf.Empty;
import com.gt.scr.domain.Gender;
import com.gt.scr.domain.User;
import com.gt.scr.movie.grpc.AccountCreateGrpcRequestDTO;
import com.gt.scr.movie.grpc.CreateAccountServiceGrpc;
import com.gt.scr.user.exception.SystemException;
import com.gt.scr.user.service.UserServiceV2;
import com.gt.scr.user.service.domain.Role;
import com.gt.scr.utils.DataEncoder;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

public class CreateAccountService extends CreateAccountServiceGrpc.CreateAccountServiceImplBase {
    private static final Logger LOG = LoggerFactory.getLogger(CreateAccountService.class);

    private final UserServiceV2 userService;
    private final DataEncoder dataEncoder;

    public CreateAccountService(UserServiceV2 userService,
                                DataEncoder dataEncoder) {
        this.userService = userService;
        this.dataEncoder = dataEncoder;
    }

    @Override
    public void createAccount(AccountCreateGrpcRequestDTO accountCreateRequestDTO, StreamObserver<Empty> responseObserver) {
        if (Role.ADMIN.toString().equals(accountCreateRequestDTO.getRole())) {
            LOG.info("ADMIN Role not allowed to call this endpoint");
            responseObserver.onError(new StatusException(Status.PERMISSION_DENIED));
        } else {
            try {
                userService.add(new User(UUID.randomUUID(),
                                accountCreateRequestDTO.getFirstName(),
                                accountCreateRequestDTO.getLastName(),
                                accountCreateRequestDTO.getUserName(),
                                encode(accountCreateRequestDTO.getPassword()),
                                accountCreateRequestDTO.getDateOfBirth(),
                                Gender.valueOf(accountCreateRequestDTO.getGender()),
                                accountCreateRequestDTO.getHomeCountry(),
                                Collections.singletonList(accountCreateRequestDTO.getRole())))
                        .onFailure(responseObserver::onError)
                        .onSuccess(event -> responseObserver.onCompleted());
            } catch(Exception e) {
                LOG.error(e.getMessage(), e);
                responseObserver.onError(e);
            }
        }
    }

    private String encode(String password) {
        try {
            return dataEncoder.encode(password);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }
}
