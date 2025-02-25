package com.consolefire.relayer.core.msgsrc;

import com.consolefire.relayer.model.source.MessageSourceProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface MessageSourceContext {

    String getIdentifier();

    void setIdentifier(String identifier);

    MessageSourceProperties getMessageSourceProperties();

    void setMessageSourceProperties(MessageSourceProperties messageSourceProperties);


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class DefaultMessageSourceContext implements MessageSourceContext {

        private String identifier;
        private MessageSourceProperties messageSourceProperties;

    }
}
