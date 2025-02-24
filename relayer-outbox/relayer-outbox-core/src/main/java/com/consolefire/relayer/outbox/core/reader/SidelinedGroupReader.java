package com.consolefire.relayer.outbox.core.reader;

import com.consolefire.relayer.core.reader.GroupFilterProperties;
import com.consolefire.relayer.core.reader.ParkedGroupReader;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.outbox.model.SidelinedGroup;
import java.util.Collection;
import java.util.Set;

public interface SidelinedGroupReader<G extends SidelinedGroup> extends ParkedGroupReader<G> {




}
