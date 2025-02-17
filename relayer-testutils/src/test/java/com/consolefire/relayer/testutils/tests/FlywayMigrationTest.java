package com.consolefire.relayer.testutils.tests;

import com.radcortez.flyway.test.annotation.DataSource;
import com.radcortez.flyway.test.annotation.FlywayTest;
import com.consolefire.relayer.testutils.data.H2InMemoryDataSourceBuilder;
import com.consolefire.relayer.testutils.data.TestDataSource;
import com.consolefire.relayer.testutils.data.TestMessageStoreMetadata;
import com.consolefire.relayer.testutils.ext.DataSourceAwareExtension;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@DataSourceAwareExtension
@FlywayTest(value = @DataSource(H2InMemoryDataSourceBuilder.class),
        additionalLocations = {"db/test-migrations"})
public class FlywayMigrationTest {

    @TestDataSource
    private javax.sql.DataSource testDataSource;

    @Test
    @SneakyThrows
    void shouldMigrateDatabase() {
        assertNotNull(testDataSource);
        @Cleanup Connection connection = testDataSource.getConnection();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        assertNotNull(databaseMetaData);
        @Cleanup ResultSet schemaResultSet = databaseMetaData.getSchemas();
        assertNotNull(schemaResultSet);
        Set<String> schemaNames = new HashSet<>();
        while (schemaResultSet.next()) {
            String schemaName = schemaResultSet.getString("TABLE_SCHEM");
            log.info("Found schemaName: {}", schemaName);
            assertNotNull(schemaName);
            schemaNames.add(schemaName.toLowerCase());
        }
        assertTrue(schemaNames.contains(TestMessageStoreMetadata.SCHEMA_MESSAGE_STORE.toLowerCase()));
    }


}
