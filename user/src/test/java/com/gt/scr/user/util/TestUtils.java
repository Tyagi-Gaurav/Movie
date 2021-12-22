package com.gt.scr.user.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.spc.domain.User;
import org.assertj.core.util.Strings;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestUtils {
    final static ObjectMapper mapper = new ObjectMapper();

    public static String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readFromString(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    public static void addToDatabase(User expectedUser,
                                     DataSource dataSource,
                                     String query) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, expectedUser.id().toString());
            preparedStatement.setString(2, expectedUser.getUsername());
            preparedStatement.setString(3, expectedUser.firstName());
            preparedStatement.setString(4, expectedUser.lastName());
            preparedStatement.setString(5, expectedUser.getPassword());
            preparedStatement.setString(6,
                    Strings.join(expectedUser.getAuthorities()).with(","));
            preparedStatement.execute();
        }
    }
}
