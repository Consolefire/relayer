package com.consolefire.relayer.core.data.tx;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionalOperation<I, O> {

    O apply(Connection connection, I input) throws SQLException;

}
