package com.consolefire.relayer.msgsrc.data.query;

public class DefaultMessageSourceQueryProvider implements MessageSourceQueryProvider {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM message_source WHERE identifier = ?";
    private static final String SAVE_OR_UPDATE_QUERY =
        "INSERT INTO message_source (identifier, configuration, state, created_at, updated_at) " +
            "VALUES (?, ?, ?, ?, ?) " +
            "ON CONFLICT (identifier) DO UPDATE SET " +
            "configuration = ?, " +
            "state = ?, " +
            "updated_at = ?";
    private static final String UPDATE_STATE_QUERY = "UPDATE message_source SET state = ?, updated_at = ? WHERE identifier = ?";
    private static final String UPDATE_CONFIGURATION_QUERY = "UPDATE message_source SET configuration = ?, updated_at = ? WHERE identifier = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM message_source";


    @Override
    public String getFindByIdQuery() {
        return FIND_BY_ID_QUERY;
    }

    @Override
    public String getSaveOrUpdateQuery() {
        return SAVE_OR_UPDATE_QUERY;
    }

    @Override
    public String getUpdateStateQuery() {
        return UPDATE_STATE_QUERY;
    }

    @Override
    public String getUpdateConfigurationQuery() {
        return UPDATE_CONFIGURATION_QUERY;
    }

    @Override
    public String getFindAllQuery() {
        return FIND_ALL_QUERY;
    }
}
