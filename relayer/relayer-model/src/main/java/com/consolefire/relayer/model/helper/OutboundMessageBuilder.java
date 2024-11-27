package com.consolefire.relayer.model.helper;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.MessageState;
import com.consolefire.relayer.model.OutboundMessage;
import com.consolefire.relayer.model.conversion.MessageHeaderConverter;
import com.consolefire.relayer.model.conversion.MessageMetadataConverter;
import com.consolefire.relayer.model.conversion.MessagePayloadConverter;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Date;

public class OutboundMessageBuilder<ID extends Serializable, PAYLOAD, HEADER, META, M extends OutboundMessage<ID>>
        extends MessageBuilder<ID, PAYLOAD, HEADER, META, M, OutboundMessageBuilder<ID, PAYLOAD, HEADER, META, M>> {

    protected MessageState state;
    protected Date relayedAt;
    protected int relayCount;

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

    public final OutboundMessageBuilder<ID, PAYLOAD, HEADER, META, M> withState(MessageState state) {
        this.state = state;
        return this;
    }

    public final OutboundMessageBuilder<ID, PAYLOAD, HEADER, META, M> withRelayedAt(Date relayedAt) {
        this.relayedAt = relayedAt;
        return this;
    }

    public final OutboundMessageBuilder<ID, PAYLOAD, HEADER, META, M> withRelayCount(int relayCount) {
        this.relayCount = relayCount;
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
            outboundMessage.setState(this.state);
            outboundMessage.setRelayedAt(this.relayedAt);
            outboundMessage.setRelayCount(this.relayCount);
        }
    }

}
