package com.gt.scr.imdb.client;

import com.gt.scr.imdb.common.AppInitializer;
import com.gt.scr.imdb.service.MovieService;
import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;

public class GetAllDataClient {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        MovieService movieService = new AppInitializer().getMovieService();
        movieService.getMovieBy("tt0000001")
                .switchIfEmpty(Mono.defer(() -> {
                    countDownLatch.countDown();
                    System.out.println("No Elements found");
                    return Mono.empty();
                }))
                .subscribe(x -> {
                    System.out.println(x);
                    countDownLatch.countDown();
                });

        countDownLatch.await();
        System.out.println(movieService.getNumberOfTitles());
    }
}
