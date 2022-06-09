package com.gt.scr.imdb.domain;

import com.gt.scr.imdb.ext.akas.domain.Aka;
import com.gt.scr.imdb.ext.people.domain.Persons;
import com.gt.scr.imdb.ext.principals.domain.Principal;

import java.io.UnsupportedEncodingException;
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

        @Override
        public String toString() {
            return "SubContent{" +
                    "ordering=" + ordering +
                    ", title='" + write(title) + '\'' +
                    ", region='" + region + '\'' +
                    ", language='" + language + '\'' +
                    ", types='" + types + '\'' +
                    ", attributes='" + attributes + '\'' +
                    ", isOriginalTitle=" + isOriginalTitle +
                    '}' + "\n";
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

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", category='" + category + '\'' +
                    ", job='" + job + '\'' +
                    ", characters='" + characters + '\'' +
                    ", birthYear='" + birthYear + '\'' +
                    ", deathYear='" + deathYear + '\'' +
                    ", primaryName='" + write(primaryName) + '\'' +
                    ", primaryProfession='" + primaryProfession + '\'' +
                    ", knownForTitles='" + knownForTitles + '\'' +
                    '}' + "\n";
        }
    }

    @Override
    public String toString() {
        return "ContentData{" +
                "contentId='" + contentId + '\'' + "\n" +
                ", subContents=" + subContents + "\n" +
                ", peoples=" + peoples +
                '}';
    }

    private static String write(String value) {
        try {
            return new String(value.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return value;
        }
    }
}
