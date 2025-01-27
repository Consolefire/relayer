package com.consolefire.relayer.util.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter<T> {

    void setValues(T source, PreparedStatement statement) throws SQLException;

}
