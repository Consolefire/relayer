package com.consolefire.relayer.sample.outbox.cfg;

import com.consolefire.relayer.outbox.core.props.OutboxConfigProperties;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RelayerDataSourceConfig {

    @Bean
    @Primary
    @Qualifier("relayer.outbox.properties")
    @ConfigurationProperties(prefix = "relayer.outbox")
    OutboxConfigProperties outboxConfigProperties() {
        return new OutboxConfigProperties();
    }

    @Bean
    @FlywayDataSource
    @Qualifier("relay-store.datasource.properties")
    @ConfigurationProperties("relayer.data-source")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @Qualifier("relay-store.datasource")
    @ConfigurationProperties("relayer.data-source")
    public DataSource secondDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


}
