package com.consolefire.relayer.sidecar.tmt.api;

import com.consolefire.relayer.msgsrc.MessageSourceRegistry;
import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message-sources")
public class MessageSourceApiController {

    private final MessageSourceRegistry messageSourceRegistry;

    @PostMapping
    public ResponseEntity<MessageSource> createMessageSource(@RequestBody MessageSource request) {
        if (request.getIdentifier() == null || request.getIdentifier().trim().isEmpty()) {
            String identifier = UUID.randomUUID().toString();
            request.setIdentifier(identifier);
        }
        request = messageSourceRegistry.register(request.getIdentifier(), request.getConfiguration());
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<MessageSource> getMessageSource(@PathVariable String identifier) {
        MessageSource messageSource = messageSourceRegistry.get(identifier);
        if (messageSource != null) {
            return new ResponseEntity<>(messageSource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<MessageSource> updateMessageSource(@PathVariable String identifier,
        @RequestBody MessageSource request) {
        MessageSource existingMessageSource = messageSourceRegistry.get(identifier);
        if (existingMessageSource != null) {
            request.setUpdatedAt(Instant.now());

            // Handle Configuration Update
            if (request.getConfiguration() != null) {
                existingMessageSource.setConfiguration(request.getConfiguration());
            }

            //Handle state update
            if (request.getState() != null) {
                existingMessageSource.setState(request.getState());
            }

            existingMessageSource.setUpdatedAt(request.getUpdatedAt());

            return new ResponseEntity<>(existingMessageSource, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<Void> deleteMessageSource(@PathVariable String identifier) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<Map<String, MessageSource>> getAllMessageSources() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
