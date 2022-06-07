package com.gt.scr.imdb.service;

import com.gt.scr.imdb.domain.ContentData;
import com.gt.scr.imdb.ext.akas.AkasReader;
import com.gt.scr.imdb.ext.akas.domain.Aka;
import com.gt.scr.imdb.ext.crew.CrewClient;
import com.gt.scr.imdb.ext.episode.EpisodesClient;
import com.gt.scr.imdb.ext.people.PeoplesClient;
import com.gt.scr.imdb.ext.people.domain.Persons;
import com.gt.scr.imdb.ext.principals.PrincipalsClient;
import com.gt.scr.imdb.ext.principals.domain.Principal;
import com.gt.scr.imdb.ext.ratings.RatingsClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.function.Function;

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
        final Mono<Aka> titleInfo = akasReader.getTitleById(contentId);
        final Mono<Principal> principalsInfo = principalsClient.getTitleById(contentId);

        return Mono.zip(titleInfo, principalsInfo)
                .zipWhen(tuple ->
                    Flux.fromIterable(tuple.getT2().subPrincipals()
                                    .stream().map(Principal.SubPrincipals::peopleId).toList())
                            .collectList()
                            .flatMap(peoplesClient::getPersonBy)
                )
                .map(data -> {
                    final Aka aka = data.getT1().getT1();
                    final Principal principal = data.getT1().getT2();
                    final Persons persons = data.getT2();

                    return new ContentData(aka.titleId(), ContentData.SubContent.from(aka.subAkas()),
                            ContentData.Person.from(principal.subPrincipals(), persons));
                });
    }
}
