package com.consolefire.relayer.outbox.core.reader;

import com.consolefire.relayer.model.source.MessageSourceProperties;
import java.util.Set;

public interface SidelinedGroupReader {

    Set<String> getAllGroups(MessageSourceProperties messageSourceProperties);

}
