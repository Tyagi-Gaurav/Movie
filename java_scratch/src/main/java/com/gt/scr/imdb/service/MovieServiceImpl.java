package com.gt.scr.imdb.service;

import com.gt.scr.imdb.domain.ContentData;
import com.gt.scr.imdb.ext.akas.AkasReader;
import com.gt.scr.imdb.ext.crew.CrewClient;
import com.gt.scr.imdb.ext.episode.EpisodesClient;
import com.gt.scr.imdb.ext.people.PeoplesClient;
import com.gt.scr.imdb.ext.principals.PrincipalsClient;
import com.gt.scr.imdb.ext.ratings.RatingsClient;
import reactor.core.publisher.Mono;

public class MovieServiceImpl implements MovieService {

    private final AkasReader akasReader;
    private final PrincipalsClient principalsClient;
    private final PeoplesClient peoplesClient;
    private final CrewClient crewClient;
    private final RatingsClient ratingsClient;
    private final EpisodesClient episodesClient;

    public MovieServiceImpl(AkasReader akasReader,
                            PrincipalsClient principalsClient,
                            PeoplesClient peoplesClient,
                            CrewClient crewClient,
                            RatingsClient ratingsClient,
                            EpisodesClient episodesClient) {
        this.akasReader = akasReader;
        this.principalsClient = principalsClient;
        this.peoplesClient = peoplesClient;
        this.crewClient = crewClient;
        this.ratingsClient = ratingsClient;
        this.episodesClient = episodesClient;
    }

    @Override
    public Mono<ContentData> getMovieBy(String contentId) {
        return akasReader.getTitleById(contentId)
                .map(aka -> new ContentData(aka.titleId(), ContentData.SubContent.from(aka.subAkas())));
    }
}
