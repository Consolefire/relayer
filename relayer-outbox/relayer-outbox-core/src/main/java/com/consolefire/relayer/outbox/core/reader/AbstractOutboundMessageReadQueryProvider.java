package com.consolefire.relayer.outbox.core.reader;

import com.consolefire.relayer.core.data.MessageReadQueryProvider;

public abstract class AbstractOutboundMessageReadQueryProvider implements MessageReadQueryProvider {


    @Override
    public String getReadQuery() {
        return buildStatement();
    }

    protected abstract String buildStatement();

}
