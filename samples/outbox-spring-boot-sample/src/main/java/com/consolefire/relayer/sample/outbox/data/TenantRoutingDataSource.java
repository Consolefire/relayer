package com.consolefire.relayer.sample.outbox.data;

import com.consolefire.relayer.sample.outbox.CurrentTenantContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class TenantRoutingDataSource extends AbstractRoutingDataSource {


    private final DataSourceRegistrar dataSourceRegistrar;

    public TenantRoutingDataSource(DataSourceRegistrar dataSourceRegistrar) {
        this.dataSourceRegistrar = dataSourceRegistrar;
    }

    @Override
    public void afterPropertiesSet() {
        setTargetDataSources(dataSourceRegistrar.getRegisteredDataSources());
        super.afterPropertiesSet();

    }

    @Override
    protected Object determineCurrentLookupKey() {
        return CurrentTenantContext.getCurrentTenantId();
    }
}
