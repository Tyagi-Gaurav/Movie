package com.gt.scr.movie.grpc_resource;

import com.gt.scr.movie.grpc.Empty;
import com.gt.scr.movie.grpc.MovieGrpcCreateRequestDTO;
import com.gt.scr.movie.grpc.MovieServiceGrpc;
import com.gt.scr.movie.service.MovieService;
import com.gt.scr.movie.service.domain.Movie;
import io.grpc.stub.StreamObserver;

import java.math.BigDecimal;
import java.util.UUID;

public class MovieGrpcResource extends MovieServiceGrpc.MovieServiceImplBase {
    private final MovieService movieService;

    public MovieGrpcResource(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void createMovie(MovieGrpcCreateRequestDTO request,
                            StreamObserver<Empty> responseObserver) {
        Movie movie = new Movie(UUID.randomUUID(),
                request.getName(),
                request.getYearProduced(),
                BigDecimal.valueOf(request.getRating()),
                System.nanoTime());

        String userId = AuthorizationInterceptor.USER_ID_KEY.get().toString();

        movieService.addMovie(UUID.fromString(userId), movie);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
