package com.consolefire.relayer.core.msgsrc;

import com.consolefire.relayer.msgsrc.MessageSourceContext;
import java.util.List;

public interface MessageSourceProvider {

    enum Type {
        INTERNAL, EXTERNAL, FIXED
    }

    MessageSourceContext getMessageSource(String identifier);

    List<MessageSourceContext> getAllRegisteredSources();

}
