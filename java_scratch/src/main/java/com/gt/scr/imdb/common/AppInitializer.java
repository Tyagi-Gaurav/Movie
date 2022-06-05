package com.gt.scr.imdb.common;

import com.gt.scr.imdb.ext.akas.AkasClient;
import com.gt.scr.imdb.ext.crew.CrewClient;
import com.gt.scr.imdb.ext.episode.EpisodesClient;
import com.gt.scr.imdb.ext.people.PeoplesClient;
import com.gt.scr.imdb.ext.principals.PrincipalsClient;
import com.gt.scr.imdb.ext.ratings.RatingsClient;
import com.gt.scr.imdb.service.MovieService;
import com.gt.scr.imdb.service.MovieServiceImpl;

import java.util.Set;

public class AppInitializer {

    private final MovieServiceImpl movieService;

    public AppInitializer() {
        final int blockCount = 1;
        Set<String> keys = Set.of("tt0000003", "tt0000001");
        AkasClient akasClient = new AkasClient(DataSourceFactory.createTSVFactory("/title.akas.tsv", blockCount, keys));
        final PrincipalsClient principalsClient = new PrincipalsClient(DataSourceFactory.createTSVFactory("/title.principals.tsv", blockCount, keys));
        final PeoplesClient peoplesClient = new PeoplesClient(DataSourceFactory.createTSVFactory("/name.basics.tsv", blockCount, keys));
        final CrewClient crewClient = new CrewClient(DataSourceFactory.createTSVFactory("/title.crew.tsv", blockCount, keys));
        final RatingsClient ratingsClient = new RatingsClient(DataSourceFactory.createTSVFactory("/title.ratings.tsv", blockCount, keys));
        final EpisodesClient episodesClient = new EpisodesClient(DataSourceFactory.createTSVFactory("/title.episode.tsv", blockCount, keys));
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
