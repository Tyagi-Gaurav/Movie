package com.gt.scr.movie.dao;

import com.gt.scr.exception.DatabaseException;
import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class StreamMetaDataRepositoryImpl implements StreamMetaDataRepository {
    @Autowired
    private DataSource dataSource;

    private static final String ID = "ID";
    private static final String MOVIE_ID = "MOVIE_ID";
    private static final String NAME = "NAME";
    private static final String SEQUENCE = "SEQUENCE";
    private static final String CREATION_TIMESTAMP = "CREATION_TIMESTAMP";
    private static final String SIZE_IN_BYTES = "SIZE_IN_BYTES";

    private static final String SCHEMA = "MOVIE_SCHEMA";

    private static final String INSERT_MOVIEMETADATA = String.format("INSERT INTO %s.MOVIE_STREAM_METADATA (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
            SCHEMA, ID, MOVIE_ID, NAME, SEQUENCE, SIZE_IN_BYTES, CREATION_TIMESTAMP);

    @Override
    public void store(MovieStreamMetaData movieStreamMetaData) {
        try (Connection connection = getConnection();
             var preparedStatement = connection.prepareStatement(INSERT_MOVIEMETADATA)) {

            preparedStatement.setString(1, movieStreamMetaData.streamId().toString());
            preparedStatement.setString(2, movieStreamMetaData.movieId().toString());
            preparedStatement.setString(3, movieStreamMetaData.streamName());
            preparedStatement.setLong(4, movieStreamMetaData.sequence());
            preparedStatement.setLong(5, movieStreamMetaData.sizeInBytes());
            preparedStatement.setLong(6, movieStreamMetaData.creationTimeStamp());
            preparedStatement.execute();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
