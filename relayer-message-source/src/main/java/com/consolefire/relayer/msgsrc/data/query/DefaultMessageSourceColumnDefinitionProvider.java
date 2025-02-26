package com.consolefire.relayer.msgsrc.data.query;

import com.consolefire.relayer.util.query.ColumnDefinition;

public class DefaultMessageSourceColumnDefinitionProvider implements MessageSourceColumnDefinitionProvider {

    @Override
    public ColumnDefinition getIdentifierColumnDefinition() {
        return new ColumnDefinition(MessageSourceDefaultJdbcConstants.COLUMN_IDENTIFIER);
    }

    @Override
    public ColumnDefinition getConfigurationColumnDefinition() {
        return new ColumnDefinition(MessageSourceDefaultJdbcConstants.COLUMN_CONFIGURATION);
    }

    @Override
    public ColumnDefinition getStateColumnDefinition() {
        return new ColumnDefinition(MessageSourceDefaultJdbcConstants.COLUMN_STATE);
    }

    @Override
    public ColumnDefinition getCreatedAtColumnDefinition() {
        return new ColumnDefinition(MessageSourceDefaultJdbcConstants.COLUMN_CREATED_AT);
    }

    @Override
    public ColumnDefinition getUpdatedAtColumnDefinition() {
        return new ColumnDefinition(MessageSourceDefaultJdbcConstants.COLUMN_UPDATED_AT);
    }
}
