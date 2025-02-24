package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.model.ParkedGroup;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import java.util.Set;

public interface ParkedGroupReader<G extends ParkedGroup> {

    Set<String> getAllGroupIdentifiers(MessageSourceProperties messageSourceProperties);

}
