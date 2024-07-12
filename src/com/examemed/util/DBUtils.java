package com.examemed.util;

import com.examemed.exception.DatabaseException;

import java.sql.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class DBUtils {

    public static <R> R executeQuery(String query, Consumer<PreparedStatement> parameterSetter, Function<ResultSet, R> resultSetProcessor) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (parameterSetter != null) {
                parameterSetter.accept(preparedStatement);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSetProcessor.apply(resultSet);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error executing query", e);
        }
    }

    public static void executeUpdate(String query, Consumer<PreparedStatement> parameterSetter) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (parameterSetter != null) {
                parameterSetter.accept(preparedStatement);
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error executing update", e);
        }
    }

    public static void executeTransaction(Consumer<Connection> transactionalCode) {
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            transactionalCode.accept(connection);

            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseException("Transaction failed", e);
        }
    }
}
