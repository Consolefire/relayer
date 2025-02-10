package com.consolefire.relayer.sample.outbox.cfg;

import com.consolefire.relayer.model.validation.MessageValidator;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.outbox.writer.OutboundMessageWriter;
import com.consolefire.relayer.util.data.DataSourceResolver;
import com.consolefire.relayer.util.validation.ValidationResult;
import com.consolefire.relayer.util.validation.Validators;
import com.consolefire.relayer.writer.MessageInsertStatementSetter;
import com.consolefire.relayer.writer.MessageWriteQueryProvider;
import com.consolefire.relayer.writer.MessageWriterTransactionSupport;
import com.consolefire.relayer.writer.impl.DefaultMessageWriterTransactionSupport;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class OutboxConfigurator {

    @Bean
    MessageWriterTransactionSupport messageWriterTransactionSupport() {
        return new DefaultMessageWriterTransactionSupport();
    }

    @Bean
    MessageValidator<UUID, OutboundMessage<UUID>> messageValidator() {
        return (value) -> Validators.NOT_NULL_VALIDATOR.validate(value);
    }

    @Bean
    MessageWriteQueryProvider messageWriteQueryProvider() {
        return () -> """
            insert into outbound_message (
                message_id,
                message_sequence,
                group_id,
                channel_name,
                payload,
                headers,
                metadata,
                state,
                created_at)
            values(
                ?,
                (select nextval('seq_outbound_message')),
                ?,
                ?,
                ?,
                ?,
                ?,
                ?,
                now()
            )""";
    }


    @Bean
    OutboundMessageWriter<UUID> uuidOutboundMessageWriter(
        DataSourceResolver dataSourceResolver,
        MessageWriterTransactionSupport messageWriterTransactionSupport,
        MessageValidator<UUID, OutboundMessage<UUID>> messageValidator,
        MessageWriteQueryProvider messageWriteQueryProvider,
        MessageInsertStatementSetter<UUID, OutboundMessage<UUID>> messageInsertStatementSetter) {
        return new OutboundMessageWriter<>(dataSourceResolver, messageValidator, messageWriteQueryProvider,
            messageInsertStatementSetter, messageWriterTransactionSupport);
    }

}
