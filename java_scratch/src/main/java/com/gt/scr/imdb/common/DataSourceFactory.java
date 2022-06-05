package com.gt.scr.imdb.common;

import java.util.Set;

public class DataSourceFactory {
    public static DataReader createTSVFactory(String fileName, int blockCount, Set<String> keys) {
        return new TSVFileReader(fileName, blockCount, keys);
    }
}
