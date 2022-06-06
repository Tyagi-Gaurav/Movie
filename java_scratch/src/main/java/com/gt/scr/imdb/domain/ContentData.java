package com.gt.scr.imdb.domain;

import com.gt.scr.imdb.ext.akas.domain.Aka;
import com.gt.scr.imdb.ext.principals.domain.Principal;

import java.util.List;

public record ContentData(String contentId,
                          List<SubContent> subContents,
                          List<Person> peoples) {

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

    public record Person(String name,
                         String category,
                         String job,
                         String characters) {
        public static List<Person> from(List<Principal.SubPrincipals> subPrincipals) {
            return subPrincipals.stream()
                    .map(subPrincipal -> new Person(subPrincipal.peopleId(),
                            subPrincipal.category(),
                            subPrincipal.job(),
                            subPrincipal.characters()))
                    .toList();
        }
    }
}
