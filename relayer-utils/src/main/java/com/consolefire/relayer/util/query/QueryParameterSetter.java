package com.consolefire.relayer.util.query;

import java.sql.SQLException;

public interface QueryParameterSetter<Q extends Query, S> {

    void setProperties(Q query, S source) throws SQLException;

}
