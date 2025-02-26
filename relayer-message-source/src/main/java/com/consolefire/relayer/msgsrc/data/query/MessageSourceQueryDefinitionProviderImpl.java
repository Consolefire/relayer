package com.consolefire.relayer.msgsrc.data.query;

import java.util.Arrays;

public record MessageSourceQueryDefinitionProviderImpl(MessageSourceParameterDefinitionProvider parameterProvider,
                                                       MessageSourceColumnDefinitionProvider columnProvider) implements
    MessageSourceQueryDefinitionProvider {

    @Override
    public QueryDefinition getFindByIdQueryDefinition() {
        return new QueryDefinition(
            "SELECT " + MessageSourceDefaultJdbcConstants.COLUMN_IDENTIFIER + ", " +
                MessageSourceDefaultJdbcConstants.COLUMN_CONFIGURATION + ", " +
                MessageSourceDefaultJdbcConstants.COLUMN_STATE + ", " +
                MessageSourceDefaultJdbcConstants.COLUMN_CREATED_AT + ", " +
                MessageSourceDefaultJdbcConstants.COLUMN_UPDATED_AT +
                " FROM " + MessageSourceDefaultJdbcConstants.TABLE_NAME + " WHERE "
                + MessageSourceDefaultJdbcConstants.COLUMN_IDENTIFIER + " = ?",
            parameterProvider.getIdentifierParameterDefinition(),
            columnProvider.getIdentifierColumnDefinition(),
            columnProvider.getConfigurationColumnDefinition(),
            columnProvider.getStateColumnDefinition(),
            columnProvider.getCreatedAtColumnDefinition(),
            columnProvider.getUpdatedAtColumnDefinition()
        );
    }

    @Override
    public QueryDefinition getSaveOrUpdateQueryDefinition() {
        return new QueryDefinition(
            "INSERT INTO " + MessageSourceDefaultJdbcConstants.TABLE_NAME + " (" +
                MessageSourceDefaultJdbcConstants.COLUMN_IDENTIFIER + ", " +
                MessageSourceDefaultJdbcConstants.COLUMN_CONFIGURATION + ", " +
                MessageSourceDefaultJdbcConstants.COLUMN_STATE + ", " +
                MessageSourceDefaultJdbcConstants.COLUMN_CREATED_AT + ", " +
                MessageSourceDefaultJdbcConstants.COLUMN_UPDATED_AT + ") " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT (" + MessageSourceDefaultJdbcConstants.COLUMN_IDENTIFIER + ") DO UPDATE SET " +
                MessageSourceDefaultJdbcConstants.COLUMN_CONFIGURATION + " = ?, " +
                MessageSourceDefaultJdbcConstants.COLUMN_STATE + " = ?, " +
                MessageSourceDefaultJdbcConstants.COLUMN_UPDATED_AT + " = ?",
            parameterProvider.getIdentifierParameterDefinition(),
            parameterProvider.getConfigurationParameterDefinition(),
            parameterProvider.getStateParameterDefinition(),
            parameterProvider.getCreatedAtParameterDefinition(),
            parameterProvider.getUpdatedAtParameterDefinition(),
            parameterProvider.getConfigurationParameterDefinitionUpdate(),
            parameterProvider.getStateParameterDefinitionUpdate(),
            parameterProvider.getUpdatedAtParameterDefinitionUpdate()
        );
    }

    @Override
    public QueryDefinition getUpdateStateQueryDefinition() {
        return new QueryDefinition(
            "UPDATE " + MessageSourceDefaultJdbcConstants.TABLE_NAME + " SET " +
                MessageSourceDefaultJdbcConstants.COLUMN_STATE + " = ?, " +
                MessageSourceDefaultJdbcConstants.COLUMN_UPDATED_AT + " = ? " +
                "WHERE " + MessageSourceDefaultJdbcConstants.COLUMN_IDENTIFIER + " = ?",
            parameterProvider.getStateParameterDefinition(),
            parameterProvider.getUpdatedAtParameterDefinition(),
            parameterProvider.getIdentifierParameterDefinition()
        );
    }

    @Override
    public QueryDefinition getUpdateConfigurationQueryDefinition() {
        return new QueryDefinition(
            "UPDATE " + MessageSourceDefaultJdbcConstants.TABLE_NAME + " SET " +
                MessageSourceDefaultJdbcConstants.COLUMN_CONFIGURATION + " = ?, " +
                MessageSourceDefaultJdbcConstants.COLUMN_UPDATED_AT + " = ? " +
                "WHERE " + MessageSourceDefaultJdbcConstants.COLUMN_IDENTIFIER + " = ?",
            parameterProvider.getConfigurationParameterDefinition(),
            parameterProvider.getUpdatedAtParameterDefinition(),
            parameterProvider.getIdentifierParameterDefinition()
        );
    }

    @Override
    public QueryDefinition getFindAllQueryDefinition() {
        return new QueryDefinition(
            "SELECT " + MessageSourceDefaultJdbcConstants.COLUMN_IDENTIFIER + ", " +
                MessageSourceDefaultJdbcConstants.COLUMN_CONFIGURATION + ", " +
                MessageSourceDefaultJdbcConstants.COLUMN_STATE + ", " +
                MessageSourceDefaultJdbcConstants.COLUMN_CREATED_AT + ", " +
                MessageSourceDefaultJdbcConstants.COLUMN_UPDATED_AT +
                " FROM " + MessageSourceDefaultJdbcConstants.TABLE_NAME,
            columnProvider.getIdentifierColumnDefinition(),
            columnProvider.getConfigurationColumnDefinition(),
            columnProvider.getStateColumnDefinition(),
            columnProvider.getCreatedAtColumnDefinition(),
            columnProvider.getUpdatedAtColumnDefinition()
        );
    }

    public record QueryDefinition(String query, Object... definitions) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            QueryDefinition that = (QueryDefinition) o;
            return query.equals(that.query) && Arrays.equals(definitions, that.definitions);
        }

        @Override
        public int hashCode() {
            int result = query.hashCode();
            result = 31 * result + Arrays.hashCode(definitions);
            return result;
        }

        @Override
        public String toString() {
            return "QueryDefinition{" +
                "query='" + query + '\'' +
                ", definitions=" + Arrays.toString(definitions) +
                '}';
        }
    }
}
