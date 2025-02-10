package ct.com.consolefire.relayer.core.processor.impl;

import com.consolefire.relayer.testutils.data.H2InMemoryDataSourceBuilder;
import com.consolefire.relayer.testutils.data.TestDataSource;
import com.consolefire.relayer.testutils.ext.DataSourceAwareExtension;
import com.radcortez.flyway.test.annotation.FlywayTest;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataSourceAwareExtension
@FlywayTest(value = @com.radcortez.flyway.test.annotation.DataSource(H2InMemoryDataSourceBuilder.class),
    additionalLocations = {"db/test-migrations"})
public class BaseMessageProcessorTest {

    @TestDataSource
    private DataSource dataSource;


}
