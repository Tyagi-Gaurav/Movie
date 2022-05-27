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
        final int blockCount = 0;
        AkasClient akasClient = new AkasClient(DataSourceFactory.createTSVFactory("/title.akas.tsv", blockCount));
        final PrincipalsClient principalsClient = new PrincipalsClient(DataSourceFactory.createTSVFactory("/title.principals.tsv", blockCount));
        final PeoplesClient peoplesClient = new PeoplesClient(DataSourceFactory.createTSVFactory("/name.basics.tsv", blockCount));
        final CrewClient crewClient = new CrewClient(DataSourceFactory.createTSVFactory("/title.crew.tsv", blockCount));
        final RatingsClient ratingsClient = new RatingsClient(DataSourceFactory.createTSVFactory("/title.ratings.tsv", blockCount));
        final EpisodesClient episodesClient = new EpisodesClient(DataSourceFactory.createTSVFactory("/title.episode.tsv", blockCount));
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
