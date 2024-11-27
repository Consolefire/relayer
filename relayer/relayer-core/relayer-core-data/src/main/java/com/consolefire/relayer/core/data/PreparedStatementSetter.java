package com.consolefire.relayer.core.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter<T> {

    void setValues(T source, PreparedStatement preparedStatement) throws SQLException;

}
