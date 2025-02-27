package com.consolefire.relayer.msgsrc.data.query;

import com.consolefire.relayer.util.query.ParameterDefinition;

public class DefaultMessageSourceParameterDefinitionProvider implements MessageSourceParameterDefinitionProvider {

    @Override
    public ParameterDefinition getIdentifierParameterDefinition() {
        return new ParameterDefinition(MessageSourceDefaultJdbcConstants.COLUMN_IDENTIFIER,
            MessageSourceDefaultJdbcConstants.COLUMN_IDENTIFIER,
            MessageSourceDefaultJdbcConstants.PARAM_INDEX_IDENTIFIER);
    }

    @Override
    public ParameterDefinition getConfigurationParameterDefinition() {
        return new ParameterDefinition(MessageSourceDefaultJdbcConstants.COLUMN_CONFIGURATION,
            MessageSourceDefaultJdbcConstants.COLUMN_CONFIGURATION,
            MessageSourceDefaultJdbcConstants.PARAM_INDEX_CONFIGURATION);
    }

    @Override
    public ParameterDefinition getStateParameterDefinition() {
        return new ParameterDefinition(MessageSourceDefaultJdbcConstants.COLUMN_STATE,
            MessageSourceDefaultJdbcConstants.COLUMN_STATE,
            MessageSourceDefaultJdbcConstants.PARAM_INDEX_STATE);
    }

    @Override
    public ParameterDefinition getCreatedAtParameterDefinition() {
        return new ParameterDefinition(MessageSourceDefaultJdbcConstants.COLUMN_CREATED_AT,
            MessageSourceDefaultJdbcConstants.COLUMN_CREATED_AT,
            MessageSourceDefaultJdbcConstants.PARAM_INDEX_CREATED_AT);
    }

    @Override
    public ParameterDefinition getUpdatedAtParameterDefinition() {
        return new ParameterDefinition(MessageSourceDefaultJdbcConstants.COLUMN_UPDATED_AT,
            MessageSourceDefaultJdbcConstants.COLUMN_UPDATED_AT,
            MessageSourceDefaultJdbcConstants.PARAM_INDEX_UPDATED_AT);
    }

    @Override
    public ParameterDefinition getConfigurationParameterDefinitionUpdate() {
        return new ParameterDefinition(MessageSourceDefaultJdbcConstants.COLUMN_CONFIGURATION,
            MessageSourceDefaultJdbcConstants.COLUMN_CONFIGURATION,
            MessageSourceDefaultJdbcConstants.PARAM_INDEX_CONFIGURATION_UPDATE);
    }

    @Override
    public ParameterDefinition getStateParameterDefinitionUpdate() {
        return new ParameterDefinition(MessageSourceDefaultJdbcConstants.COLUMN_STATE,
            MessageSourceDefaultJdbcConstants.COLUMN_STATE,
            MessageSourceDefaultJdbcConstants.PARAM_INDEX_STATE_UPDATE);
    }

    @Override
    public ParameterDefinition getUpdatedAtParameterDefinitionUpdate() {
        return new ParameterDefinition(MessageSourceDefaultJdbcConstants.COLUMN_UPDATED_AT,
            MessageSourceDefaultJdbcConstants.COLUMN_UPDATED_AT,
            MessageSourceDefaultJdbcConstants.PARAM_INDEX_UPDATED_AT_UPDATE);
    }
}
