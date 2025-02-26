package com.consolefire.relayer.msgsrc.data.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSource implements Serializable {

    public enum State {
        REGISTERED, UNREGISTERED, ACTIVE, INACTIVE
    }

    private String identifier;
    // for now a modifiable map
    @Default
    @NonNull
    private JsonNode configuration = new ObjectNode(JsonNodeFactory.instance);
    private State state;
    private Instant createdAt;
    private Instant updatedAt;


}
