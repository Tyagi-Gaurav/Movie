package com.gt.scr.imdb.common;

public class DataSourceFactory {
    public static DataReader createTSVFactory(String fileName, int blockCount) {
        return new TSVFileReader(fileName, blockCount);
    }
}
