package com.gt.scr.movie;

import com.gt.scr.movie.grpc.AccountCreateGrpcRequestDTO;
import com.gt.scr.movie.grpc.LoginGrpcRequestDTO;
import com.gt.scr.movie.grpc.MovieGrpcCreateRequestDTO;
import org.apache.commons.lang3.RandomStringUtils;

public class TestBuilder {
    public static AccountCreateGrpcRequestDTO accountCreateGrpc() {
        return AccountCreateGrpcRequestDTO.newBuilder()
                .setFirstName("testFirstName")
                .setLastName("testLastName")
                .setPassword(RandomStringUtils.randomAlphanumeric(7))
                .setUserName(RandomStringUtils.randomAlphanumeric(7))
                .setRole("ADMIN").build();
    }


    public static LoginGrpcRequestDTO loginCreateGrpc(AccountCreateGrpcRequestDTO accountCreateGrpcRequestDTO) {
        return LoginGrpcRequestDTO.newBuilder()
                .setUserName(accountCreateGrpcRequestDTO.getUserName())
                .setPassword(accountCreateGrpcRequestDTO.getPassword())
                .build();
    }

    public static MovieGrpcCreateRequestDTO movieCreate() {
        return MovieGrpcCreateRequestDTO.newBuilder()
                .setName("TestMovieName")
                .setRating(10.0)
                .setYearProduced(2020)
                .build();
    }
}
