package com.gt.scr.movie.dao;

import com.gt.scr.movie.service.domain.ImmutableMovie;
import com.gt.scr.movie.service.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.empty;
import static java.util.Optional.of;

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
    public Optional<Movie> findMovieBy(UUID movieId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP FROM MOVIE where ID = ?")) {
            preparedStatement.setString(1, movieId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return of(extractMovieFrom(resultSet));
                } else {
                    return empty();
                }
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Movie> findMovieBy(UUID userId, String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP FROM MOVIE where NAME = ?")) {
            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return of(extractMovieFrom(resultSet));
                } else {
                    return empty();
                }
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public List<Movie> getAllMoviesForUser(UUID userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP FROM MOVIE WHERE USER_ID = ?")) {
            preparedStatement.setString(1, userId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Movie> movies = new ArrayList<>();
                while (resultSet.next()) {
                    movies.add(extractMovieFrom(resultSet));
                }

                return movies;
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(UUID movieId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM MOVIE where id = ?")) {
            preparedStatement.setString(1, movieId.toString());
            preparedStatement.execute();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Movie updatedMovie) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
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
    }

    @Override
    public void create(UUID userId, Movie movie) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
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
    }

    private Movie extractMovieFrom(ResultSet resultSet) throws SQLException {
        return ImmutableMovie.builder()
                .id(UUID.fromString(resultSet.getString(ID)))
                .name(resultSet.getString(NAME))
                .yearProduced(resultSet.getInt(YEAR_PRODUCED))
                .rating(resultSet.getBigDecimal(RATING))
                .creationTimeStamp(resultSet.getLong(CREATION_TIMESTAMP))
                .build();
    }
}
