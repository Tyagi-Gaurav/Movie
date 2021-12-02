package com.gt.scr.movie.dao;

import com.gt.scr.movie.service.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.gt.scr.exception.DatabaseException;

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
    private static final String NAME = "NAME";
    private static final String YEAR_PRODUCED = "YEAR_PRODUCED";
    private static final String RATING = "RATING";
    private static final String CREATION_TIMESTAMP = "CREATION_TIMESTAMP";

    @Override
    public Mono<Movie> findMovieBy(UUID movieId) {
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(
                     "SELECT ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP FROM MOVIE where ID = ?")) {
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
             var preparedStatement = connection.prepareStatement(
                     "SELECT ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP FROM MOVIE where NAME = ? and USER_ID = ?")) {
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
             var preparedStatement = connection.prepareStatement(
                     "SELECT ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP FROM MOVIE WHERE USER_ID = ?")) {
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
             var preparedStatement = connection.prepareStatement(
                     "DELETE FROM MOVIE where id = ?")) {
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
             var preparedStatement = connection.prepareStatement(
                     "UPDATE MOVIE SET NAME = ?, " +
                             "YEAR_PRODUCED = ?, " +
                             "RATING = ?" +
                             " WHERE ID = ?")) {

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
             var preparedStatement = connection.prepareStatement(
                     "INSERT INTO MOVIE (ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP, USER_ID) " +
                             "VALUES (?, ?, ?, ?, ?, ?)")) {

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
