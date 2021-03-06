package com.gt.scr.imdb.ext.akas.domain;

import com.gt.scr.imdb.ext.akas.AkaRow;

import java.util.List;

public record Aka(String titleId,
                  List<SubAkas> subAkas) {

    public record SubAkas(int ordering,
                          String title,
                          String region,
                          String language,
                          String types,
                          String attributes,
                          boolean isOriginalTitle) {

        public static SubAkas from(AkaRow akaConsumer) {
            return new SubAkas(akaConsumer.ordering(),
                    akaConsumer.title(),
                    akaConsumer.region(),
                    akaConsumer.language(),
                    akaConsumer.types(),
                    akaConsumer.attributes(),
                    akaConsumer.isOriginalTitle());
        }
    }
}

