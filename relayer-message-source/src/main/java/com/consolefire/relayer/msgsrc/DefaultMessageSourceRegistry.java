package com.consolefire.relayer.msgsrc;

import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import com.consolefire.relayer.msgsrc.data.entity.MessageSource.State;
import com.consolefire.relayer.msgsrc.data.event.MessageSourceActivatedEvent;
import com.consolefire.relayer.msgsrc.data.event.MessageSourceDeactivatedEvent;
import com.consolefire.relayer.msgsrc.data.event.MessageSourceEvent;
import com.consolefire.relayer.msgsrc.data.event.MessageSourceEventNotifier;
import com.consolefire.relayer.msgsrc.data.event.MessageSourceRegisteredEvent;
import com.consolefire.relayer.msgsrc.data.event.MessageSourceUnregisteredEvent;
import com.consolefire.relayer.msgsrc.data.repo.MessageSourceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultMessageSourceRegistry implements MessageSourceRegistry {

    private final MessageSourceRepository messageSourceRepository;
    private final MessageSourceEventNotifier messageSourceEventNotifier;


    @Override
    public <E extends MessageSourceEvent> void notify(E event) {
        log.debug("Sending MessageSourceEvent: {}", event);
        messageSourceEventNotifier.send(event);
    }

    @Override
    public MessageSource register(String identifier, JsonNode configurations) {
        log.info("Registering MessageSource with identifier: {}", identifier);
        if (messageSourceRepository.findById(identifier) != null) {
            log.warn("MessageSource with identifier {} already exists.", identifier);
            throw new IllegalArgumentException("MessageSource with identifier " + identifier + " already exists.");
        }

        MessageSource messageSource = MessageSource.builder()
            .identifier(identifier)
            .configuration(configurations)
            .state(State.REGISTERED)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();

        int result = messageSourceRepository.saveOrUpdate(messageSource);
        if (result > 0) {
            log.info("MessageSource registered successfully: {}", identifier);
            notify(new MessageSourceRegisteredEvent(messageSource.getIdentifier(), messageSource.getState(),
                messageSource.getConfiguration()));
            return messageSource;
        } else {
            log.error("Failed to register MessageSource: {}", identifier);
            return null;
        }
    }

    @Override
    public boolean unregister(String identifier) {
        log.info("Unregistering MessageSource with identifier: {}", identifier);
        MessageSource messageSource = messageSourceRepository.findById(identifier);
        if (messageSource == null) {
            log.warn("MessageSource with identifier {} not found.", identifier);
            return false;
        }

        messageSource.setState(State.UNREGISTERED);
        messageSource.setUpdatedAt(Instant.now());
        int result = messageSourceRepository.updateState(identifier, State.UNREGISTERED);
        if (result > 0) {
            log.info("MessageSource unregistered successfully: {}", identifier);
            notify(new MessageSourceUnregisteredEvent(identifier));
            return true;
        } else {
            log.error("Failed to unregister MessageSource: {}", identifier);
            return false;
        }
    }

    @Override
    public boolean activate(String identifier) {
        log.info("Activating MessageSource with identifier: {}", identifier);
        MessageSource messageSource = messageSourceRepository.findById(identifier);
        if (messageSource == null || (messageSource.getState() != State.REGISTERED
            && messageSource.getState() != State.INACTIVE)) {
            log.warn("MessageSource with identifier {} cannot be activated.", identifier);
            return false;
        }

        messageSource.setState(State.ACTIVE);
        messageSource.setUpdatedAt(Instant.now());
        int result = messageSourceRepository.updateState(identifier, State.ACTIVE);
        if (result > 0) {
            log.info("MessageSource activated successfully: {}", identifier);
            notify(new MessageSourceActivatedEvent(identifier));
            return true;
        } else {
            log.error("Failed to activate MessageSource: {}", identifier);
            return false;
        }
    }

    @Override
    public boolean deactivate(String identifier) {
        log.info("Deactivating MessageSource with identifier: {}", identifier);
        MessageSource messageSource = messageSourceRepository.findById(identifier);
        if (messageSource == null || messageSource.getState() != State.ACTIVE) {
            log.warn("MessageSource with identifier {} cannot be deactivated.", identifier);
            return false;
        }

        messageSource.setState(State.INACTIVE);
        messageSource.setUpdatedAt(Instant.now());
        int result = messageSourceRepository.updateState(identifier, State.INACTIVE);
        if (result > 0) {
            log.info("MessageSource deactivated successfully: {}", identifier);
            notify(new MessageSourceDeactivatedEvent(identifier));
            return true;
        } else {
            log.error("Failed to deactivate MessageSource: {}", identifier);
            return false;
        }
    }

    @Override
    public MessageSource get(String identifier) {
        log.debug("Getting MessageSource with identifier: {}", identifier);
        MessageSource messageSource = messageSourceRepository.findById(identifier);
        if (messageSource == null) {
            log.debug("MessageSource with identifier: {} not found", identifier);
        }
        return messageSource;
    }

}
