package com.consolefire.relayer.model.builder;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.MessageState;
import com.consolefire.relayer.model.ModelConstants;
import com.consolefire.relayer.model.conversion.DefaultObjectToStringConverter;
import com.consolefire.relayer.model.conversion.MessageHeaderConverter;
import com.consolefire.relayer.model.conversion.MessageMetadataConverter;
import com.consolefire.relayer.model.conversion.MessagePayloadConverter;
import com.consolefire.relayer.model.helper.MessageGroupIdGenerator;
import com.consolefire.relayer.model.helper.MessageIdGenerator;
import com.consolefire.relayer.model.helper.MessageSequenceGenerator;
import com.consolefire.relayer.model.outbox.OutboundMessageBuilder;
import java.time.Instant;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class MessageBuilder<ID extends Serializable, PAYLOAD, HEADER, META, M extends Message<ID>, B extends MessageBuilder<ID, PAYLOAD, HEADER, META, M, B>>
        implements Supplier<M> {

    protected ID messageId;
    protected Supplier<ID> messageIdSupplier;
    protected Long messageSequence;
    protected Supplier<Long> messageSequenceSupplier;
    protected String groupId;
    protected String channelName;
    protected PAYLOAD payload;
    protected HEADER headers;
    protected META metadata;
    protected MessageState state;
    protected Instant createdAt;
    protected Instant updatedAt;
    protected Optional<MessageIdGenerator<ID>> messageIdGenerator;
    protected Optional<MessageSequenceGenerator> messageSequenceGenerator;
    protected Optional<MessageGroupIdGenerator<PAYLOAD, META>> messageGroupIdGenerator;
    protected Optional<MessagePayloadConverter<PAYLOAD>> messagePayloadConverter;
    protected Optional<MessageHeaderConverter<HEADER>> messageHeaderConverter;
    protected Optional<MessageMetadataConverter<META>> messageMetadataConverter;
    public static final DefaultObjectToStringConverter<Object> stringConverter = new DefaultObjectToStringConverter<>();

    public MessageBuilder() {
        this(null, null,
                null, null, null, null);
    }

    public MessageBuilder(
            MessageIdGenerator<ID> messageIdGenerator,
            MessageSequenceGenerator messageSequenceGenerator) {
        this(messageIdGenerator, messageSequenceGenerator,
                null, null, null, null);
    }

    public MessageBuilder(
            MessageIdGenerator<ID> messageIdGenerator,
            MessageSequenceGenerator messageSequenceGenerator,
            MessageGroupIdGenerator<PAYLOAD, META> messageGroupIdGenerator,
            MessagePayloadConverter<PAYLOAD> messagePayloadConverter,
            MessageHeaderConverter<HEADER> messageHeaderConverter,
            MessageMetadataConverter<META> messageMetadataConverter) {
        this.messageIdGenerator = Optional.ofNullable(messageIdGenerator);
        this.messageSequenceGenerator = Optional.ofNullable(messageSequenceGenerator);
        this.messageGroupIdGenerator = Optional.ofNullable(messageGroupIdGenerator);
        this.messagePayloadConverter = Optional.ofNullable(messagePayloadConverter);
        this.messageHeaderConverter = Optional.ofNullable(messageHeaderConverter);
        this.messageMetadataConverter = Optional.ofNullable(messageMetadataConverter);
    }

    protected abstract B self();

    public final B withMessageId(ID messageId) {
        this.messageId = messageId;
        return self();
    }

    public final B withMessageIdSupplier(Supplier<ID> messageIdSupplier) {
        this.messageIdSupplier = messageIdSupplier;
        return self();
    }

    public final B withMessageSequence(Long sequence) {
        this.messageSequence = sequence;
        return self();
    }

    public final B withMessageSequenceSupplier(Supplier<Long> sequenceSupplier) {
        this.messageSequenceSupplier = sequenceSupplier;
        return self();
    }

    public final B withGroupId(String groupId) {
        this.groupId = groupId;
        return self();
    }

    public final B withChannelName(String channelName) {
        this.channelName = channelName;
        return self();
    }

    public final B withState(MessageState state) {
        this.state = state;
        return self();
    }

    public final B withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return self();
    }

    public final B withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return self();
    }

    public final B withPayload(PAYLOAD payload) {
        this.payload = payload;
        return self();
    }

    public final B withHeaders(HEADER headers) {
        this.headers = headers;
        return self();
    }

    public final B withMetadata(META metadata) {
        this.metadata = metadata;
        return self();
    }

    public final B usingMessageIdGenerator(
            MessageIdGenerator<ID> messageIdGenerator) {
        this.messageIdGenerator = Optional.ofNullable(messageIdGenerator);
        return self();
    }

    public final B usingMessageSequenceGenerator(
            MessageSequenceGenerator messageSequenceGenerator) {
        this.messageSequenceGenerator = Optional.ofNullable(messageSequenceGenerator);
        return self();
    }

    public final B usingMessageGroupIdGenerator(
            MessageGroupIdGenerator<PAYLOAD, META> messageGroupIdGenerator) {
        this.messageGroupIdGenerator = Optional.ofNullable(messageGroupIdGenerator);
        return self();
    }

    public final B usingMessagePayloadConverter(
            MessagePayloadConverter<PAYLOAD> messagePayloadConverter) {
        this.messagePayloadConverter = Optional.ofNullable(messagePayloadConverter);
        return self();
    }

    public final B usingMessageHeaderConverter(
            MessageHeaderConverter<HEADER> messageHeaderConverter) {
        this.messageHeaderConverter = Optional.ofNullable(messageHeaderConverter);
        return self();
    }

    public final B usingMessageMetadataConverter(
            MessageMetadataConverter<META> messageMetadataConverter) {
        this.messageMetadataConverter = Optional.ofNullable(messageMetadataConverter);
        return self();
    }

    protected abstract @NonNull M initMessage();

    public final @NonNull M build() {
        M message = initMessage();

        message.setMessageId(
                Optional.ofNullable(this.messageId) // set the ID provided
                        .orElseGet(() -> Optional.ofNullable(this.messageIdSupplier).map(Supplier::get) // set from the ID Supplier
                                .orElseGet(() -> this.messageIdGenerator.map(MessageIdGenerator::generate) // set from the ID Generator
                                        .orElse(null))) // otherwise NULL
        );

        message.setState(this.state);

        message.setCreatedAt(Optional.ofNullable(this.createdAt).orElse(Instant.now()));
        message.setUpdatedAt(this.updatedAt);

        message.setHeaders(this.messageHeaderConverter
                .map(c -> c.convert(headers))
                .orElseGet(() -> stringConverter.convert(headers)));

        message.setPayload(this.messagePayloadConverter
                .map(c -> c.convert(payload))
                .orElseGet(() -> stringConverter.convert(payload)));

        message.setMetadata(this.messageMetadataConverter
                .map(c -> c.convert(metadata))
                .orElseGet(() -> stringConverter.convert(metadata)));

        message.setGroupId(Optional.ofNullable(groupId)
                .orElseGet(() -> messageGroupIdGenerator
                        .map(g -> g.generate(payload, metadata)).orElse(ModelConstants.DEFAULT_MESSAGE_GROUP)));

        setAdditionalProperties(message);

        return message;
    }

    @Override
    public final M get() {
        return this.build();
    }

    protected abstract void setAdditionalProperties(Message<ID> message);
}
