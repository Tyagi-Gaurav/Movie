package com.gt.scr.movie.dao;

import com.gt.scr.exception.DatabaseException;
import com.gt.scr.movie.service.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MovieMySQLRepository implements MovieRepository {
    @Autowired
    private DataSource dataSource;

    private static final String ID = "ID";
    private static final String USER_ID = "USER_ID";
    private static final String NAME = "NAME";
    private static final String YEAR_PRODUCED = "YEAR_PRODUCED";
    private static final String RATING = "RATING";
    private static final String CREATION_TIMESTAMP = "CREATION_TIMESTAMP";

    private static final String SCHEMA = "MOVIE_SCHEMA";
    private static final String FIND_BY_ID = String.format("SELECT %s, %s, %s, %s, %s FROM %s.MOVIE where ID = ?",
            ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP, SCHEMA);

    private static final String FIND_BY_ID_NAME = String.format("SELECT %s, %s, %s, %s, %s FROM %s.MOVIE where NAME = ? and USER_ID = ?",
            ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP, SCHEMA);

    private static final String GET_ALL_MOVIES_FOR_USER = String.format("SELECT %s, %s, %s, %s, %s FROM %s.MOVIE where USER_ID = ?",
            ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP, SCHEMA);

    private static final String DELETE_MOVIE = String.format("DELETE FROM %s.MOVIE where id = ?", SCHEMA);

    private static final String UPDATE_MOVIE = String.format("UPDATE %s.MOVIE SET %s = ?, %s = ?, %s = ? WHERE ID = ?",
            SCHEMA, NAME, YEAR_PRODUCED, RATING);

    private static final String INSERT_MOVIE = String.format("INSERT INTO %s.MOVIE (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
            SCHEMA, ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP, USER_ID);

    @Override
    public Mono<Movie> findMovieBy(UUID movieId) {
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setString(1, movieId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Mono.just(extractMovieFrom(resultSet));
                } else {
                    return Mono.empty();
                }
            }
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

    @Override
    public Mono<Movie> findMovieBy(UUID userId, String name) {
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_NAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, userId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Mono.just(extractMovieFrom(resultSet));
                } else {
                    return Mono.empty();
                }
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public Flux<Movie> getAllMoviesForUser(UUID userId) {
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(GET_ALL_MOVIES_FOR_USER)) {
            preparedStatement.setString(1, userId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Movie> movies = new ArrayList<>();
                while (resultSet.next()) {
                    movies.add(extractMovieFrom(resultSet));
                }

                return Flux.fromIterable(movies);
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public Mono<Void> delete(UUID movieId) {
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_MOVIE)) {
            preparedStatement.setString(1, movieId.toString());
            preparedStatement.execute();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        return Mono.empty();
    }

    @Override
    public Mono<Void> update(Movie updatedMovie) {
        try (Connection connection = getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_MOVIE)) {

            preparedStatement.setString(1, updatedMovie.name());
            preparedStatement.setInt(2, updatedMovie.yearProduced());
            preparedStatement.setBigDecimal(3, updatedMovie.rating());
            preparedStatement.setString(4, updatedMovie.id().toString());

            preparedStatement.execute();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        return Mono.empty();
    }

    @Override
    public Mono<Void> create(UUID userId, Movie movie) {
        try (Connection connection = getConnection();
             var preparedStatement = connection.prepareStatement(INSERT_MOVIE)) {

            preparedStatement.setString(1, movie.id().toString());
            preparedStatement.setString(2, movie.name());
            preparedStatement.setInt(3, movie.yearProduced());
            preparedStatement.setBigDecimal(4, movie.rating());
            preparedStatement.setLong(5, movie.creationTimeStamp());
            preparedStatement.setString(6, userId.toString());
            preparedStatement.execute();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        return Mono.empty();
    }

    private Movie extractMovieFrom(ResultSet resultSet) throws SQLException {
        return new Movie(UUID.fromString(resultSet.getString(ID)),
                resultSet.getString(NAME),
                resultSet.getInt(YEAR_PRODUCED),
                resultSet.getBigDecimal(RATING).setScale(1, RoundingMode.UNNECESSARY),
                resultSet.getLong(CREATION_TIMESTAMP));
    }
}
