package com.consolefire.relayer.util.query.exec;

import com.consolefire.relayer.util.query.Parameter;
import com.consolefire.relayer.util.query.Query;
import java.util.List;
import javax.sql.DataSource;

public interface QueryExecutor<Q extends Query, R> {

    R execute(DataSource dataSource, Q query, List<Parameter<?>> parameters);

}
