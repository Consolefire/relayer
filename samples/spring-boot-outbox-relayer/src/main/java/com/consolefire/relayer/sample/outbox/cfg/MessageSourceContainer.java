package com.consolefire.relayer.sample.outbox.cfg;

import com.consolefire.relayer.core.msgsrc.MessageSourceContext;
import com.consolefire.relayer.core.msgsrc.MessageSourceContext.DefaultMessageSourceContext;
import com.consolefire.relayer.core.msgsrc.MessageSourceProvider;
import com.consolefire.relayer.core.msgsrc.MessageSourceRegistrar;
import com.consolefire.relayer.core.msgsrc.MessageSourceResolver;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.outbox.core.props.OutboxConfigProperties;
import com.consolefire.relayer.sample.outbox.msgsrc.FixedMessageSourceProvider;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageSourceContainer
    extends FixedMessageSourceProvider
    implements MessageSourceProvider, MessageSourceRegistrar, MessageSourceResolver {

    private final Map<String, MessageSourceProperties> dynamicRegisteredMessageSourceProperties
        = new HashMap<>();

    public MessageSourceContainer(OutboxConfigProperties outboxConfigProperties) {
        super(outboxConfigProperties);
    }

    @Override
    public MessageSourceContext register(MessageSourceProperties messageSourceProperties) {
        if (!contains(messageSourceProperties.getIdentifier())) {
            dynamicRegisteredMessageSourceProperties.put(messageSourceProperties.getIdentifier(),
                messageSourceProperties);
        }
        MessageSourceContext context = super.getMessageSource(messageSourceProperties.getIdentifier());
        if (null == context) {
            new DefaultMessageSourceContext(messageSourceProperties.getIdentifier(),
                messageSourceProperties);
        }
        return context;
    }


    @Override
    public MessageSourceContext resolve(String identifier) {
        MessageSourceContext context = super.getMessageSource(identifier);
        if (null != context) {
            return context;
        }
        if (dynamicRegisteredMessageSourceProperties.containsKey(identifier)) {
            return new DefaultMessageSourceContext(identifier,
                dynamicRegisteredMessageSourceProperties.get(identifier));
        }
        return null;
    }
}
