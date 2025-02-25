package com.consolefire.relayer.sample.outbox.msgsrc;

import com.consolefire.relayer.core.msgsrc.MessageSourceContext;
import com.consolefire.relayer.core.msgsrc.MessageSourceContext.DefaultMessageSourceContext;
import com.consolefire.relayer.core.msgsrc.MessageSourceProvider;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.outbox.core.props.OutboxConfigProperties;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FixedMessageSourceProvider implements MessageSourceProvider {

    protected final Map<String, MessageSourceProperties> messageSourceProperties;

    public FixedMessageSourceProvider(OutboxConfigProperties outboxConfigProperties) {
        this.messageSourceProperties = outboxConfigProperties.getMessageSourceProperties()
            .stream().collect(Collectors.toUnmodifiableMap(a -> a.getIdentifier(), a -> a));
    }

    @Override
    public MessageSourceContext getMessageSource(String identifier) {
        MessageSourceProperties properties = messageSourceProperties.get(identifier);
        DefaultMessageSourceContext context = new DefaultMessageSourceContext();
        context.setMessageSourceProperties(properties);
        context.setIdentifier(identifier);
        return context;
    }

    @Override
    public List<MessageSourceContext> getAllRegisteredSources() {
        return messageSourceProperties.entrySet().stream()
            .map((e) ->
                new DefaultMessageSourceContext(e.getKey(), e.getValue())
            )
            .collect(Collectors.toList());
    }

    protected boolean contains(String identifier) {
        return messageSourceProperties.containsKey(identifier);
    }

}
