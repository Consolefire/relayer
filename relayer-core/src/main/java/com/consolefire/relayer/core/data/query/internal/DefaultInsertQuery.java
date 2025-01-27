package com.consolefire.relayer.core.data.query.internal;

import static java.lang.String.format;

import com.consolefire.relayer.core.data.query.InsertQuery;
import com.consolefire.relayer.core.data.query.QueryType;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class DefaultInsertQuery extends AbstractQuery implements InsertQuery {


    public static final String NAME_SEPARATOR = ", ";
    public static final String INSERT_SQL_PATTERN_NO_SCHEMA = "insert into %s (%s) values (%s)";
    public static final String INSERT_SQL_PATTERN = "insert into %s.%s (%s) values (%s)";

    private final String schemaName;
    private final String tableName;
    private final Map<String, Integer> columnIndexes;

    public DefaultInsertQuery(
        String schemaName, @NonNull String tableName, @NonNull Map<String, Integer> columnIndexes) {
        super(QueryType.INSERT);
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columnIndexes = Optional.ofNullable(columnIndexes)
            .map(Collections::unmodifiableMap)
            .orElse(Collections.emptyMap());
    }

    @Override
    public int indexOf(String columnName) {
        return columnIndexes.get(columnName);
    }

    @Override
    public int getParameterCount() {
        return columnIndexes.keySet().size();
    }

    @Override
    protected String buildStatement() {
        if (Optional.ofNullable(schemaName).map(String::trim).filter(Predicate.not(String::isBlank)).isPresent()) {
            return String.format(INSERT_SQL_PATTERN, schemaName, tableName, toColumnNames(), toQuestions());
        }
        return format(INSERT_SQL_PATTERN_NO_SCHEMA, tableName, toColumnNames(), toQuestions());
    }

    @Override
    protected String resolveStatement() {
        return "";
    }

    private String toQuestions() {
        return columnIndexes.values().stream().sorted().map(x -> "?")
            .collect(Collectors.joining(NAME_SEPARATOR));
    }

    private String toColumnNames() {
        return columnIndexes.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
            .collect(Collectors.joining(NAME_SEPARATOR));
    }
}
