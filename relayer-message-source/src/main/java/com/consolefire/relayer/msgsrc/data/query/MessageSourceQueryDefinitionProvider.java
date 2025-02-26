package com.consolefire.relayer.msgsrc.data.query;

import com.consolefire.relayer.msgsrc.data.query.MessageSourceQueryDefinitionProviderImpl.QueryDefinition;

public interface MessageSourceQueryDefinitionProvider {

    QueryDefinition getFindByIdQueryDefinition();

    QueryDefinition getSaveOrUpdateQueryDefinition();

    QueryDefinition getUpdateStateQueryDefinition();

    QueryDefinition getUpdateConfigurationQueryDefinition();

    QueryDefinition getFindAllQueryDefinition();


}
