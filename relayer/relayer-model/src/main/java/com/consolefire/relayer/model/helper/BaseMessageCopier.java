package com.consolefire.relayer.model.helper;

import com.consolefire.relayer.model.Message;

import java.io.*;

public abstract class BaseMessageCopier<ID extends Serializable, M extends Message<ID>> implements MessageCopier<ID, M> {


    @Override
    public M copy(M message) {
        M copiedMessage = initTargetMessage();
        if (null == copiedMessage) {
            throw new RuntimeException("Cannot initiate target message");
        }
        copiedMessage.setMessageId(message.getMessageId());
        copiedMessage.setMessageSequence(message.getMessageSequence());
        copiedMessage.setGroupId(message.getGroupId());
        copiedMessage.setHeaders(message.getHeaders());
        copiedMessage.setPayload(message.getPayload());
        copiedMessage.setMetadata(message.getMetadata());
        copiedMessage.setCreatedAt(message.getCreatedAt());
        copiedMessage.setUpdatedAt(message.getUpdatedAt());
        copyExtendedProperties(message, copiedMessage);
        return copiedMessage;
    }

    protected abstract void copyExtendedProperties(M sourceMessage, M copiedMessage);

    protected abstract M initTargetMessage();

    public M copySerializable(M message) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
             ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            objectOutputStream.writeObject(this);
            return (M) objectInputStream.readObject();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
