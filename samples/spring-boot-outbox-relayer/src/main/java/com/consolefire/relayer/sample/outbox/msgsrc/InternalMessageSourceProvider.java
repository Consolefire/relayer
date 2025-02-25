package com.consolefire.relayer.sample.outbox.msgsrc;

import com.consolefire.relayer.core.msgsrc.MessageSourceContext;
import com.consolefire.relayer.core.msgsrc.MessageSourceProvider;
import java.util.List;

public class InternalMessageSourceProvider implements MessageSourceProvider {

    @Override
    public MessageSourceContext getMessageSource(String identifier) {
        return null;
    }

    @Override
    public List<MessageSourceContext> getAllRegisteredSources() {
        return List.of();
    }
}
