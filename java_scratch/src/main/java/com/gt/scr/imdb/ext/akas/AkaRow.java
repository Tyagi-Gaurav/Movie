package com.gt.scr.imdb.ext.akas;

import static org.assertj.core.api.Assertions.assertThat;

public record AkaRow(String titleId,
                     int ordering,
                     String title,
                     String region,
                     String language,
                     String types,
                     String attributes,
                     boolean isOriginalTitle) {

    public static AkaRow from(String tsvRow) {
        //System.out.println(tsvRow);
        try {
            String[] split = tsvRow.split("\t");
            String titleId = split[0];
            assertThat(titleId).describedAs("Title Id cannot  be null").isNotNull();
            int ordering = Integer.parseInt(split[1]);
            String title = split[2];
            String region = split[3];
            String language = split[4];
            String types = split[5];
            String attributes = split[6];
            boolean isOriginalTitle = !"\\N".equals(split[7]) && Integer.parseInt(split[7]) == 1;
            return new AkaRow(titleId, ordering, title, region, language, types, attributes, isOriginalTitle);
        } catch (Exception e) {
            System.err.println("Exception while reading " + tsvRow);
            throw new IllegalStateException(e);
        }
    }
}
