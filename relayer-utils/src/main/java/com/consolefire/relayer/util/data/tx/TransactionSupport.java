package com.consolefire.relayer.util.data.tx;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import javax.sql.DataSource;

public interface TransactionSupport {

    default <T> T executeTransaction(DataSource dataSource, TransactionalOperation<T> operation,
        Consumer<SQLException> exceptionHandler) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = operation.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                if (exceptionHandler != null) {
                    exceptionHandler.accept(e);
                }
                throw e;
            }
        } catch (SQLException e) {
            if (exceptionHandler != null) {
                exceptionHandler.accept(e);
            }
            throw new RuntimeException(e);
        }
    }

}
