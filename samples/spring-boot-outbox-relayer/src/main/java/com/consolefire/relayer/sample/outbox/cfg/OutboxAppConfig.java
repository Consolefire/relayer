package com.consolefire.relayer.sample.outbox.cfg;

import com.consolefire.relayer.core.data.MessageReadQueryProvider;
import com.consolefire.relayer.core.data.MessageRowMapper;
import com.consolefire.relayer.core.reader.MessageFilterProperties;
import com.consolefire.relayer.core.tasks.AbstractMessageReaderTask;
import com.consolefire.relayer.core.tasks.MessageReaderTask;
import com.consolefire.relayer.outbox.core.data.AbstractOutboundMessageRowMapper;
import com.consolefire.relayer.outbox.core.reader.DefaultOutboundMessageReadQueryProvider;
import com.consolefire.relayer.outbox.core.reader.OutboundMessageReader;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.util.data.DataSourceResolver;
import com.consolefire.relayer.util.data.PreparedStatementSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.UUID;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class OutboxAppConfig {


    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    @Bean
    MessageReadQueryProvider messageReadQueryProvider() {
        return new DefaultOutboundMessageReadQueryProvider();
    }

    @Bean
    MessageRowMapper<UUID, OutboundMessage<UUID>> messageRowMapper() {
        return new AbstractOutboundMessageRowMapper<UUID>() {
            @Override
            @SneakyThrows
            protected UUID mapMessageId(ResultSet resultSet) {
                String id = resultSet.getString("message_id");
                return Optional.ofNullable(id).map(UUID::fromString).orElse(null);
            }
        };
    }

    @Bean
    OutboundMessageReader<UUID> outboundMessageReader(
        DataSourceResolver dataSourceResolver,
        MessageReadQueryProvider messageReadQueryProvider,
        MessageRowMapper<UUID, OutboundMessage<UUID>> messageRowMapper) {
        PreparedStatementSetter<MessageFilterProperties> preparedStatementSetter
            = (f, s) -> {
            s.setInt(1, Optional.ofNullable(f.getMaxAttemptCount()).orElse(3));
            s.setLong(2, Optional.ofNullable(f.getLimit()).orElse(1l));
        };
        return new OutboundMessageReader<>(dataSourceResolver, messageReadQueryProvider, preparedStatementSetter,
            messageRowMapper);
    }

    @Bean
    MessageReaderTask<UUID, OutboundMessage<UUID>> outboundMessageReaderTask(
        OutboundMessageReader<UUID> outboundMessageReader) {
        return new AbstractMessageReaderTask<>(outboundMessageReader);
    }

}
