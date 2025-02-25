package com.consolefire.relayer.sample.outbox.cfg;

import com.consolefire.relayer.util.data.DataSourceRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class MessageSourceRoutingDataSource extends AbstractRoutingDataSource {


    private final DataSourceRegistrar dataSourceRegistrar;

    public MessageSourceRoutingDataSource(DataSourceRegistrar dataSourceRegistrar) {
        this.dataSourceRegistrar = dataSourceRegistrar;
    }

    @Override
    public void afterPropertiesSet() {
        setTargetDataSources(dataSourceRegistrar.getRegisteredDataSources());
        super.afterPropertiesSet();

    }

    @Override
    protected Object determineCurrentLookupKey() {
        return CurrentMessageSourceContext.getCurrentSourceId();
    }
}
