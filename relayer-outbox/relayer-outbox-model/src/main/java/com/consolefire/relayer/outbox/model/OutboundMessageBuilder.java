package com.consolefire.relayer.outbox.model;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.builder.MessageBuilder;
import com.consolefire.relayer.model.conversion.MessageHeaderConverter;
import com.consolefire.relayer.model.conversion.MessageMetadataConverter;
import com.consolefire.relayer.model.conversion.MessagePayloadConverter;
import com.consolefire.relayer.model.helper.MessageGroupIdGenerator;
import com.consolefire.relayer.model.helper.MessageIdGenerator;
import com.consolefire.relayer.model.helper.MessageSequenceGenerator;
import java.io.Serializable;
import java.time.Instant;
import lombok.NonNull;

public class OutboundMessageBuilder<ID extends Serializable, PAYLOAD, HEADER, META, M extends OutboundMessage<ID>>
    extends MessageBuilder<ID, PAYLOAD, HEADER, META, M, OutboundMessageBuilder<ID, PAYLOAD, HEADER, META, M>> {

    private Instant relayedAt;
    private int relayCount;
    private String relayError;

    public OutboundMessageBuilder() {
    }

    public OutboundMessageBuilder(
        MessageIdGenerator<ID> messageIdGenerator,
        MessageSequenceGenerator messageSequenceGenerator) {
        super(messageIdGenerator, messageSequenceGenerator);
    }

    public OutboundMessageBuilder(
        MessageIdGenerator<ID> messageIdGenerator,
        MessageSequenceGenerator messageSequenceGenerator,
        MessageGroupIdGenerator<PAYLOAD, META> messageGroupIdGenerator,
        MessagePayloadConverter<PAYLOAD> messagePayloadConverter,
        MessageHeaderConverter<HEADER> messageHeaderConverter,
        MessageMetadataConverter<META> messageMetadataConverter) {
        super(messageIdGenerator, messageSequenceGenerator, messageGroupIdGenerator,
            messagePayloadConverter, messageHeaderConverter, messageMetadataConverter);
    }

    @Override
    protected OutboundMessageBuilder<ID, PAYLOAD, HEADER, META, M> self() {
        return this;
    }



    public final OutboundMessageBuilder<ID, PAYLOAD, HEADER, META, M> withRelayedAt(Instant relayedAt) {
        this.relayedAt = relayedAt;
        return this;
    }

    public final OutboundMessageBuilder<ID, PAYLOAD, HEADER, META, M> withRelayCount(int relayCount) {
        this.relayCount = relayCount;
        return this;
    }

    public final OutboundMessageBuilder<ID, PAYLOAD, HEADER, META, M> withRelayError(String relayError) {
        this.relayError = relayError;
        return this;
    }

    @Override
    protected @NonNull M initMessage() {
        return (M) new OutboundMessage<>();
    }

    @Override
    protected final void setAdditionalProperties(Message<ID> message) {
        if (message instanceof OutboundMessage) {
            OutboundMessage<ID> outboundMessage = (OutboundMessage<ID>) message;
            outboundMessage.setChannelName(channelName);
            outboundMessage.setRelayedAt(this.relayedAt);
            outboundMessage.setRelayCount(this.relayCount);
            outboundMessage.setRelayError(this.relayError);
        }
    }

}
