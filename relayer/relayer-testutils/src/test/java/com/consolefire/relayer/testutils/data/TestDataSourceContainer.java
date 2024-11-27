package com.consolefire.relayer.testutils.data;

import lombok.Getter;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.sql.DataSource;

public class TestDataSourceContainer implements ExtensionContext.Store.CloseableResource {

    @Getter
    private final DataSource dataSource;

    public TestDataSourceContainer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void close() throws Throwable {
        // nothing to clean
    }
}
