package com.consolefire.relayer.util.data.tx;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionalOperation<T> {

    T execute(Connection connection) throws SQLException;

}
