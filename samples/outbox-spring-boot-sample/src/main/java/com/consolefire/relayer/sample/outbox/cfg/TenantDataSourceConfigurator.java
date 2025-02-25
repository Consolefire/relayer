package com.consolefire.relayer.sample.outbox.cfg;

import com.consolefire.relayer.sample.outbox.OutboxConfigProperties;
import com.consolefire.relayer.sample.outbox.data.TenantRoutingDataSource;
import com.consolefire.relayer.util.data.DataSourceRegistrar;
import io.micrometer.core.instrument.MeterRegistry;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TenantDataSourceConfigurator {

    @Bean
    @Primary
    DataSource dataSource(OutboxConfigProperties outboxConfigProperties, MeterRegistry meterRegistry,
        DataSourceRegistrar dataSourceRegistrar) {
        return new TenantRoutingDataSource(dataSourceRegistrar);
    }

}
