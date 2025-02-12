package com.consolefire.relayer.outbox.core.reader;

import com.consolefire.relayer.core.data.MessageReadQueryProvider;

public abstract class AbstractOutboxMessageReadQueryProvider implements MessageReadQueryProvider {


    @Override
    public String getReadQuery() {
        return buildStatement();
    }

    protected abstract String buildStatement();

}
