package com.consolefire.relayer.msgsrc.data.query;

public interface MessageSourceQueryProvider {

    String getFindByIdQuery();

    String getSaveOrUpdateQuery();

    String getUpdateStateQuery();

    String getUpdateConfigurationQuery();

    String getFindAllQuery();

}
