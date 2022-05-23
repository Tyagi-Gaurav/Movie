package com.gt.scr.imdb.common;

import com.gt.scr.imdb.ext.akas.AkasClient;
import com.gt.scr.imdb.service.MovieService;
import com.gt.scr.imdb.service.MovieServiceImpl;

public class AppInitializer {

    private final MovieServiceImpl movieService;

    public AppInitializer() {
        AkasClient akasClient = new AkasClient(DataSourceFactory.createTSVFactory("/title.akas.tsv"));
        movieService = new MovieServiceImpl(akasClient);
    }

    public MovieService getMovieService() {
        return movieService;
    }
}
