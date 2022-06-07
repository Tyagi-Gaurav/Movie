package com.gt.scr.imdb.domain;

import com.gt.scr.imdb.ext.akas.domain.Aka;
import com.gt.scr.imdb.ext.people.domain.Persons;
import com.gt.scr.imdb.ext.principals.domain.Principal;

import java.util.List;
import java.util.Map;

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
                         String characters,
                         String birthYear,
                         String deathYear,
                         String primaryName,
                         String primaryProfession,
                         String knownForTitles) {
        public static List<Person> from(List<Principal.SubPrincipals> principals,
                                        Persons subPrincipals) {
            final Map<String, com.gt.scr.imdb.ext.people.domain.Person> stringPersonMap = subPrincipals.personList();
            return principals.stream()
                    .map(subPrincipal -> {
                        final com.gt.scr.imdb.ext.people.domain.Person person = stringPersonMap.get(subPrincipal.peopleId());
                        return new Person(subPrincipal.peopleId(),
                                subPrincipal.category(),
                                subPrincipal.job(),
                                subPrincipal.characters(),
                                person.birthYear(),
                                person.deathYear(),
                                person.primaryName(),
                                person.primaryProfession(),
                                person.knownForTitles());
                    })
                    .toList();
        }
    }
}
