package com.gt.scr.imdb.common;

import com.gt.scr.imdb.ext.akas.AkasClient;
import com.gt.scr.imdb.ext.crew.CrewClient;
import com.gt.scr.imdb.ext.episode.EpisodesClient;
import com.gt.scr.imdb.ext.people.PeoplesClient;
import com.gt.scr.imdb.ext.principals.PrincipalsClient;
import com.gt.scr.imdb.ext.ratings.RatingsClient;
import com.gt.scr.imdb.service.MovieService;
import com.gt.scr.imdb.service.MovieServiceImpl;

public class AppInitializer {

    private final MovieServiceImpl movieService;

    public AppInitializer() {
        AkasClient akasClient = new AkasClient(DataSourceFactory.createTSVFactory("/title.akas.tsv", 1));
        final PrincipalsClient principalsClient = new PrincipalsClient(DataSourceFactory.createTSVFactory("/title.principals.tsv", 1));
        final PeoplesClient peoplesClient = new PeoplesClient(DataSourceFactory.createTSVFactory("/name.basics.tsv", 1));
        final CrewClient crewClient = new CrewClient(DataSourceFactory.createTSVFactory("/title.crew.tsv", 1));
        final RatingsClient ratingsClient = new RatingsClient(DataSourceFactory.createTSVFactory("/title.ratings.tsv", 1));
        final EpisodesClient episodesClient = new EpisodesClient(DataSourceFactory.createTSVFactory("/title.episode.tsv", 1));
        movieService = new MovieServiceImpl(akasClient,
                principalsClient,
                peoplesClient,
                crewClient,
                ratingsClient,
                episodesClient);
    }

    public MovieService getMovieService() {
        return movieService;
    }
}
