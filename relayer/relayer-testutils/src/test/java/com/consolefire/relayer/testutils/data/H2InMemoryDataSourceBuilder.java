package com.consolefire.relayer.testutils.data;

import com.radcortez.flyway.test.junit.DataSourceInfo;
import com.radcortez.flyway.test.junit.DataSourceProvider;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.sql.DataSource;

public class H2InMemoryDataSourceBuilder extends TestDataSourceBuilder implements DataSourceProvider {

    private static final String JDBC_URL = "jdbc:h2:mem:test_message_store;MODE=PostgreSQL;DB_CLOSE_DELAY=-1";
    private static final String ROOT = "root";

    @Override
    public String getUrl() {
        return JDBC_URL;
    }

    @Override
    public String getUsername() {
        return ROOT;
    }

    @Override
    public String getPassword() {
        return ROOT;
    }

    @Override
    public DataSource buildDataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(JDBC_URL);
        ds.setUser(ROOT);
        ds.setPassword(ROOT);
        return ds;
    }

    @Override
    public DataSourceInfo getDatasourceInfo(ExtensionContext extensionContext) {
        return DataSourceInfo.config(JDBC_URL, ROOT, ROOT);
    }
}
