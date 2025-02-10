package com.consolefire.relayer.sample.outbox.data;

import java.util.Map;
import javax.sql.DataSource;

public interface DataSourceRegistrar {

    void register(String id, DataSource dataSource);

    void unregister(String id);

    Map<Object, Object> getRegisteredDataSources();

}
