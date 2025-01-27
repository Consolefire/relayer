package com.consolefire.relayer.writer.impl;

import com.consolefire.relayer.writer.MessageWriterTransactionSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DefaultMessageWriterTransactionSupport implements MessageWriterTransactionSupport {

    @Override
    public int usingTransaction(Connection connection, PreparedStatement statement) throws SQLException {
        final boolean autoCommitState = connection.getAutoCommit();
        try {
            connection.setAutoCommit(false);
            return statement.executeUpdate();
        } finally {
            connection.setAutoCommit(autoCommitState);
        }
    }
}
