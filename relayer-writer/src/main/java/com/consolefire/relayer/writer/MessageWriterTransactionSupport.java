package com.consolefire.relayer.writer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface MessageWriterTransactionSupport {

    int usingTransaction(Connection connection, PreparedStatement statement) throws SQLException;

}
