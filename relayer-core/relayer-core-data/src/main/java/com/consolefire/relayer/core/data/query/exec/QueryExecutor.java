package com.consolefire.relayer.core.data.query.exec;

import com.consolefire.relayer.core.data.query.Parameter;
import com.consolefire.relayer.core.data.query.Query;
import java.util.List;
import javax.sql.DataSource;

public interface QueryExecutor<Q extends Query, R> {

    R execute(DataSource dataSource, Q query, List<Parameter<?>> parameters);

}
