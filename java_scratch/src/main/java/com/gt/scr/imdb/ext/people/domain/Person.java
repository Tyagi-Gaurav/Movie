package com.gt.scr.imdb.ext.people.domain;

import com.gt.scr.imdb.ext.people.PersonRow;

public record Person(String personId,
                     String primaryName,
                     String birthYear,
                     String deathYear,
                     String primaryProfession,
                     String knownForTitles) {

    public static Person from(PersonRow personRow) {
        return new Person(personRow.personId(),
                personRow.primaryName(),
                personRow.birthYear(),
                personRow.deathYear(),
                personRow.primaryProfession(),
                personRow.knownForTitles());
    }
}
