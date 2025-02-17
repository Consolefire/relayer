package com.consolefire.relayer.testutils.tests;

import com.consolefire.relayer.testutils.data.TestDataSource;
import com.consolefire.relayer.testutils.ext.DataSourceAwareExtension;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataSourceAwareExtension
public class JdbcConnectionTest {

    @TestDataSource
    private DataSource dataSource;


    @Test
    @SneakyThrows
    void testConnection() {
        assertNotNull(dataSource);
        @Cleanup Connection connection = dataSource.getConnection();
        assertNotNull(connection);
        assertAll(
                () -> assertNotNull(connection.getMetaData()),
                () -> assertNotNull(connection.getMetaData().getDatabaseProductName())
        );
    }
}
