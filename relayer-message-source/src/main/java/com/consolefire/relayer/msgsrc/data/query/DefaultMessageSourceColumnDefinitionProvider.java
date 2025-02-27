package com.consolefire.relayer.msgsrc.data.query;

import com.consolefire.relayer.util.query.ColumnDefinition;

public class DefaultMessageSourceColumnDefinitionProvider implements MessageSourceColumnDefinitionProvider {

    @Override
    public ColumnDefinition getIdentifierColumnDefinition() {
        return new ColumnDefinition(MessageSourceDefaultJdbcConstants.COLUMN_IDENTIFIER, 1);
    }

    @Override
    public ColumnDefinition getConfigurationColumnDefinition() {
        return new ColumnDefinition(MessageSourceDefaultJdbcConstants.COLUMN_CONFIGURATION, 2);
    }

    @Override
    public ColumnDefinition getStateColumnDefinition() {
        return new ColumnDefinition(MessageSourceDefaultJdbcConstants.COLUMN_STATE, 3);
    }

    @Override
    public ColumnDefinition getCreatedAtColumnDefinition() {
        return new ColumnDefinition(MessageSourceDefaultJdbcConstants.COLUMN_CREATED_AT, 4);
    }

    @Override
    public ColumnDefinition getUpdatedAtColumnDefinition() {
        return new ColumnDefinition(MessageSourceDefaultJdbcConstants.COLUMN_UPDATED_AT, 5);
    }
}
