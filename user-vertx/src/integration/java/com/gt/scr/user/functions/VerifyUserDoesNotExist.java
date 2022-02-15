package com.gt.scr.user.functions;

import com.gt.scr.user.resource.domain.LoginResponseDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public record VerifyUserDoesNotExist() implements BiConsumer<DataSource, LoginResponseDTO> {
    private static final String GET_USER_BY_ID = "SELECT * FROM USER_SCHEMA.USER_TABLE WHERE ID = ?";

    @Override
    public void accept(DataSource dataSource, LoginResponseDTO loginResponseDTO) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)) {

            preparedStatement.setString(1, loginResponseDTO.id().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            assertThat(resultSet.next())
                    .describedAs("Did not expect to find user.").isFalse();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
