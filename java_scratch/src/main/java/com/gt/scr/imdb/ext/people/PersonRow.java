package com.gt.scr.imdb.ext.people;

import static org.assertj.core.api.Assertions.assertThat;

public record PersonRow(String personId,
                        String primaryName,
                        String birthYear,
                        String deathYear,
                        String primaryProfession,
                        String knownForTitles) {

    public static PersonRow from(String tsvRow) {
        try {
            String[] split = tsvRow.split("\t");
            String personId = split[0];
            assertThat(personId).describedAs("Person Id cannot  be null").isNotNull();
            String primaryName = split[1];
            String birthYear = split[2];
            String deathYear = split[3];
            String profession = split[4];
            String knownForTitles = split[5];
            return new PersonRow(personId, primaryName, birthYear, deathYear, profession, knownForTitles);
        } catch (Exception e) {
            System.err.println("Exception while reading: " + tsvRow);
            throw new IllegalStateException(e);
        }
    }
}
