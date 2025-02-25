package com.consolefire.relayer.core.msgsrc;

import java.util.List;

public interface MessageSourceProvider {

    enum Type {
        INTERNAL, EXTERNAL, FIXED
    }

    MessageSourceContext getMessageSource(String identifier);

    List<MessageSourceContext> getAllRegisteredSources();

}
