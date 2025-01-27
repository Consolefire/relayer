package com.consolefire.relayer.util.data;

import javax.sql.DataSource;

public interface DataSourceResolver {

    DataSource resolve(String identifier);

}
