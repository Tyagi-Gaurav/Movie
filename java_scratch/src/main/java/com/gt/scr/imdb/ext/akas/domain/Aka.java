package com.gt.scr.imdb.ext.akas.domain;

import com.gt.scr.imdb.ext.akas.AkaRow;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Aka(String titleId,
                  List<SubAkas> subAkas) {

    public static Aka from(AkaRow akaConsumer) {
        SubAkas subAkas = SubAkas.from(akaConsumer);
        return new Aka(akaConsumer.titleId(), List.of(subAkas));
    }

    public Aka add(AkaRow akaConsumer) {
        return new Aka(akaConsumer.titleId(),
                Stream.concat(subAkas().stream(),
                                Stream.of(SubAkas.from(akaConsumer)))
                        .collect(Collectors.toList()));
    }

    public String title() {
        return subAkas().stream().filter(SubAkas::isOriginalTitle)
                .findFirst()
                .map(SubAkas::title)
                .orElseThrow(() -> new IllegalStateException("No Original Title found."));
    }

    public record SubAkas(int ordering,
                          String title,
                          String region,
                          String language,
                          String types,
                          String attributes,
                          boolean isOriginalTitle) {

        private static SubAkas from(AkaRow akaConsumer) {
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

