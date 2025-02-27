package com.consolefire.relayer.sidecar.tmt.data.repository;

import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import com.consolefire.relayer.msgsrc.data.repo.MessageSourceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MessageSourceJdbcRepository implements MessageSourceRepository {

    private final DataSource dataSource;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public MessageSource findById(String identifier) {
        String sql = "SELECT identifier, state, configuration, created_at, updated_at FROM message_source WHERE identifier = ?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, identifier);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToMessageSource(resultSet);
            } else {
                return null;
            }

        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding MessageSource by ID", e);
        }
    }

    @Override
    public int save(MessageSource messageSource) {
        String sql = "INSERT INTO message_source (identifier, state, configuration, updated_at) VALUES (?, ?, ?, ?) "
            + "ON CONFLICT (identifier) "
            + "DO UPDATE SET state = ?, configuration = ?, updated_at = ?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, messageSource.getIdentifier());
            preparedStatement.setString(2, messageSource.getState().name());
            preparedStatement.setString(3, toJson(messageSource.getConfiguration()));
            preparedStatement.setTimestamp(4, Timestamp.from(messageSource.getUpdatedAt()));
            preparedStatement.setString(5, messageSource.getState().name());
            preparedStatement.setString(6, toJson(messageSource.getConfiguration()));
            preparedStatement.setTimestamp(7, Timestamp.from(messageSource.getUpdatedAt()));

            return preparedStatement.executeUpdate();

        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving MessageSource", e);
        }
    }

    @Override
    public int updateState(String id, MessageSource.State state) {
        String sql = "UPDATE message_source SET state = ?, updated_at = ? WHERE identifier = ?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, state.name());
            preparedStatement.setTimestamp(2, Timestamp.from(Instant.now()));
            preparedStatement.setString(3, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating MessageSource state", e);
        }
    }

    @Override
    public int updateConfiguration(String id, JsonNode configuration) {
        String sql = "UPDATE message_source SET configuration = ?, updated_at = ? WHERE identifier = ?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, toJson(configuration));
            preparedStatement.setTimestamp(2, Timestamp.from(Instant.now()));
            preparedStatement.setString(3, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating MessageSource configuration", e);
        }
    }

    private MessageSource mapResultSetToMessageSource(ResultSet resultSet)
        throws SQLException, JsonProcessingException {
        return MessageSource.builder()
            .identifier(resultSet.getString("identifier"))
            .state(MessageSource.State.valueOf(resultSet.getString("state")))
            .configuration(fromJson(resultSet.getString("configuration")))
            .createdAt(resultSet.getTimestamp("created_at").toInstant())
            .updatedAt(resultSet.getTimestamp("updated_at").toInstant())
            .build();
    }

    private String toJson(JsonNode configuration) throws JsonProcessingException {
        log.info("{}", configuration);
        return objectMapper.writeValueAsString(configuration);
    }

    private JsonNode fromJson(String json) throws JsonProcessingException {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return objectMapper.readValue(json, JsonNode.class);
    }

    @Override
    public List<MessageSource> findAll() {
        String sql = "SELECT identifier, state, configuration, created_at, updated_at FROM message_source";
        List<MessageSource> messageSources = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MessageSource messageSource = mapResultSetToMessageSource(resultSet);
                messageSources.add(messageSource);
            }

        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding all MessageSources", e);
        }
        return messageSources;
    }
}