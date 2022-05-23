package com.gt.scr.imdb.common;

public class DataSourceFactory {
    public static DataReader createTSVFactory(String fileName) {
        return new TSVFileReader(fileName);
    }
}
