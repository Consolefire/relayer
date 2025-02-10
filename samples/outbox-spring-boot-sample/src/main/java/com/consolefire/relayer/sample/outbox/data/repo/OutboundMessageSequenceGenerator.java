package com.consolefire.relayer.sample.outbox.data.repo;

import static java.lang.String.format;

import com.consolefire.relayer.model.helper.MessageSequenceGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OutboundMessageSequenceGenerator implements MessageSequenceGenerator {

    private static final String SEQUENCE_NAME = "seq_outbound_message";
    private static final String NEXT_VAL_SQL;

    static {
        NEXT_VAL_SQL = format("SELECT nextval('%s')", SEQUENCE_NAME);
    }

    private final DataSource dataSource;


    @Override
    public Long next() {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(NEXT_VAL_SQL);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Failed to generate sequence from: " + NEXT_VAL_SQL);
    }
}
