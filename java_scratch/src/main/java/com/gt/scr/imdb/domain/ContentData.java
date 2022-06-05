package com.gt.scr.imdb.domain;

import com.gt.scr.imdb.ext.akas.AkaRow;
import com.gt.scr.imdb.ext.akas.domain.Aka;

import java.util.List;

public record ContentData(String contentId,
                          List<SubContent> subContents) {

    public record SubContent(int ordering,
                          String title,
                          String region,
                          String language,
                          String types,
                          String attributes,
                          boolean isOriginalTitle) {

        public static List<SubContent> from(List<Aka.SubAkas> subAkas) {
            return subAkas.stream()
                    .map(subAka ->
                        new SubContent(subAka.ordering(),
                                subAka.title(),
                                subAka.region(),
                                subAka.language(),
                                subAka.types(),
                                subAka.attributes(),
                                subAka.isOriginalTitle()))
                    .toList();
        }
    }
}
