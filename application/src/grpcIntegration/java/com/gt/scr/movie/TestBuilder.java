package com.gt.scr.movie;

import com.gt.scr.movie.grpc.AccountCreateGrpcRequestDTO;
import com.gt.scr.movie.grpc.LoginGrpcRequestDTO;
import com.gt.scr.movie.grpc.MovieGrpcCreateRequestDTO;

public class TestBuilder {
    public static AccountCreateGrpcRequestDTO accountCreate() {
        return AccountCreateGrpcRequestDTO.newBuilder()
                .setFirstName("testFirstName")
                .setLastName("testLastName")
                .setPassword("testPassword")
                .setUserName("testUserName")
                .setRole("ADMIN").build();
    }

    public static LoginGrpcRequestDTO loginCreate() {
        return LoginGrpcRequestDTO.newBuilder()
                .setUserName("testUserName")
                .setPassword("testPassword")
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
