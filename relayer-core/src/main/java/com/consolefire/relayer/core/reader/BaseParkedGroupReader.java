package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.core.data.ParkedGroupReadQueryProvider;
import com.consolefire.relayer.model.ParkedGroup;
import com.consolefire.relayer.model.source.InvalidMessageSourceProperties;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.util.data.DataAccessException;
import com.consolefire.relayer.util.data.DataSourceResolver;
import com.consolefire.relayer.util.data.InvalidDataSourceException;
import com.consolefire.relayer.util.data.InvalidSqlException;
import com.consolefire.relayer.util.data.PreparedStatementSetter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BaseParkedGroupReader<G extends ParkedGroup> implements ParkedGroupReader<G> {

    private final DataSourceResolver dataSourceResolver;
    private final ParkedGroupReadQueryProvider parkedGroupReadQueryProvider;
    private final PreparedStatementSetter<GroupFilterProperties> preparedStatementSetter;


    @Override
    public Set<String> getAllGroupIdentifiers(MessageSourceProperties messageSourceProperties) {
        if (null == messageSourceProperties || null == messageSourceProperties.getIdentifier()
            || messageSourceProperties.getIdentifier().isBlank()) {
            throw new InvalidMessageSourceProperties("Message source undefined");
        }
        DataSource dataSource = dataSourceResolver.resolve(messageSourceProperties.getIdentifier());
        if (null == dataSource) {
            throw new InvalidDataSourceException(
                "Could not resolve datasource for source: " + messageSourceProperties.getIdentifier());
        }
        String readSql = parkedGroupReadQueryProvider.getReadQueryForAllGroupIdentifiers();
        log.debug("Using sql: {}", readSql);
        if (null == readSql || readSql.isBlank()) {
            throw new InvalidSqlException("Invalid read SQL: null/empty");
        }
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(readSql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                Set<String> mCollection = new HashSet<>();
                while (resultSet.next()) {
                    String gid = resultSet.getString(1);
                    mCollection.add(gid);
                }
                return mCollection;
            } catch (SQLException e) {
                throw new DataAccessException("Failed to execute Query", e);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
