package com.consolefire.relayer.msgsrc.data.query;

import com.consolefire.relayer.util.query.ParameterDefinition;

public interface MessageSourceParameterDefinitionProvider {

    ParameterDefinition getIdentifierParameterDefinition();

    ParameterDefinition getConfigurationParameterDefinition();

    ParameterDefinition getStateParameterDefinition();

    ParameterDefinition getCreatedAtParameterDefinition();

    ParameterDefinition getUpdatedAtParameterDefinition();

    ParameterDefinition getConfigurationParameterDefinitionUpdate();

    ParameterDefinition getStateParameterDefinitionUpdate();

    ParameterDefinition getUpdatedAtParameterDefinitionUpdate();
}
