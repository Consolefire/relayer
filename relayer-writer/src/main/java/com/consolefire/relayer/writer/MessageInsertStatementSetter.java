package com.consolefire.relayer.writer;

import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageInsertStatementSetter<ID extends Serializable, M extends Message<ID>> {

    public void setProperties(PreparedStatement statement, M source) throws SQLException {

    }
}
