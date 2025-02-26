package com.consolefire.relayer.msgsrc.data.query;

import com.consolefire.relayer.util.query.ColumnDefinition;

public interface MessageSourceColumnDefinitionProvider {

    ColumnDefinition getIdentifierColumnDefinition();

    ColumnDefinition getConfigurationColumnDefinition();

    ColumnDefinition getStateColumnDefinition();

    ColumnDefinition getCreatedAtColumnDefinition();

    ColumnDefinition getUpdatedAtColumnDefinition();
}
