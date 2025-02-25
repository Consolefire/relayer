package com.consolefire.relayer.util.data;

import java.util.Map;
import javax.sql.DataSource;

public interface DataSourceRegistrar {

    void register(String id, DataSource dataSource);

    void unregister(String id);

    Map<Object, Object> getRegisteredDataSources();

}
