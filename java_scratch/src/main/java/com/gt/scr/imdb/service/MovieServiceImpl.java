package com.gt.scr.imdb.service;

import com.gt.scr.imdb.domain.ContentData;
import com.gt.scr.imdb.ext.akas.AkasReader;
import reactor.core.publisher.Mono;

public class MovieServiceImpl implements MovieService {

    private final AkasReader akasReader;

    public MovieServiceImpl(AkasReader akasReader) {
        this.akasReader = akasReader;
    }

    @Override
    public Mono<ContentData> getMovieBy(String contentId) {
        return akasReader.getTitleById(contentId)
                .map(aka -> new ContentData(aka.titleId(), aka.title()));
    }

    @Override
    public long getNumberOfTitles() {
        return akasReader.getTotalNumberOfTitles();
    }
}
