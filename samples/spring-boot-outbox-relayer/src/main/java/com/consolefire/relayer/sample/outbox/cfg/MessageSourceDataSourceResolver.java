package com.consolefire.relayer.sample.outbox.cfg;

import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.outbox.core.props.OutboxConfigProperties;
import com.consolefire.relayer.util.data.DataSourceRegistrar;
import com.consolefire.relayer.util.data.DataSourceResolver;
import com.consolefire.relayer.util.data.cfg.DataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageSourceDataSourceResolver implements DataSourceRegistrar, DataSourceResolver {

    private final ConcurrentHashMap<String, DataSource> registeredDataSources
        = new ConcurrentHashMap<>();

    private final MeterRegistry meterRegistry;

    public MessageSourceDataSourceResolver(
        OutboxConfigProperties outboxConfigProperties,
        MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        buildDataSources(outboxConfigProperties);
    }

    private void buildDataSources(OutboxConfigProperties outboxConfigProperties) {
        Set<MessageSourceProperties> messageSourceProperties = outboxConfigProperties.getMessageSourceProperties();

        if (null != messageSourceProperties && !messageSourceProperties.isEmpty()) {
            for (MessageSourceProperties tenant : messageSourceProperties) {
                DataSource dataSource = buildDataSource(tenant.getDataSource());
                register(tenant.getIdentifier(), dataSource);
            }
        }
    }

    private DataSource buildDataSource(DataSourceProperties dataSourceProperties) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dataSourceProperties.getUrl()); //jdbc:postgresql://host:port/database
        hikariConfig.setUsername(dataSourceProperties.getUsername());
        hikariConfig.setPassword(dataSourceProperties.getPassword());
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setSchema(dataSourceProperties.getSchemaName());
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        hikariDataSource.setMetricRegistry(meterRegistry);
        if (dataSourceProperties.isTestConnectionOnStart()) {
            try (Connection connection = hikariDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(dataSourceProperties.getTestSql());
                ResultSet resultSet = statement.executeQuery()) {
                log.info("Test success: {}", resultSet.next());
            } catch (SQLException e) {
                throw new RuntimeException("Failed to test datasource", e);
            }
        }
        return hikariDataSource;
    }

    @PreDestroy
    public final void cleanup() {

    }

    @Override
    public Map<Object, Object> getRegisteredDataSources() {
        return Collections.unmodifiableMap(registeredDataSources);
    }

    public void register(String id, DataSource dataSource) {
        registeredDataSources.put(id, dataSource);
    }

    public void unregister(String id) {
        if (registeredDataSources.containsKey(id)) {
            registeredDataSources.remove(id);
        }
    }

    @Override
    public DataSource resolve(String identifier) {
        return Optional.ofNullable(registeredDataSources.get(identifier))
            .orElseThrow(() -> new RuntimeException("DataSource not found for identifier: " + identifier));
    }
}
