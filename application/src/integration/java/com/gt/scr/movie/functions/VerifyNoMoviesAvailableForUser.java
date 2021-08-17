package com.gt.scr.movie.functions;

import com.gt.scr.movie.resource.domain.LoginResponseDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class VerifyNoMoviesAvailableForUser implements BiConsumer<DataSource, LoginResponseDTO> {
    private static final String GET_MOVIES_FOR_USER = "SELECT ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP FROM MOVIE WHERE USER_ID = ?";

    @Override
    public void accept(DataSource dataSource, LoginResponseDTO loginResponseDTO) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_MOVIES_FOR_USER)) {

            preparedStatement.setString(1, loginResponseDTO.id().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            assertThat(resultSet.next()).describedAs("Did not expect to find any movies for a deleted user.").isFalse();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
