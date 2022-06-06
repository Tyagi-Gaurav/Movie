package com.gt.scr.imdb.ext.principals;

import static org.assertj.core.api.Assertions.assertThat;

public record PrincipalRow(String titleId,
                           int ordering,
                           String nameId,
                           String category,
                           String job,
                           String characters) {

    public static PrincipalRow from(String tsvRow) {
        try {
            String[] split = tsvRow.split("\t");
            String titleId = split[0];
            assertThat(titleId).describedAs("Title Id cannot  be null").isNotNull();
            int ordering = Integer.parseInt(split[1]);
            String nameId = split[2];
            String category = split[3];
            String job = split[4];
            String characters = split[5];
            return new PrincipalRow(titleId, ordering, nameId, category, job, characters);
        } catch (Exception e) {
            System.err.println("Exception while reading " + tsvRow);
            throw new IllegalStateException(e);
        }
    }
}
