package com.consolefire.relayer.outbox.core.reader;

import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.outbox.model.SidelinedGroup;
import com.consolefire.relayer.util.data.DataSourceResolver;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultSidelinedGroupReader<G extends SidelinedGroup> implements SidelinedGroupReader<G> {

    private final DataSourceResolver dataSourceResolver;
    private final SidelinedGroupReadQueryProvider sidelinedGroupReadQueryProvider;
    
    @Override
    public Set<String> getAllGroupIdentifiers(MessageSourceProperties messageSourceProperties) {
        return Set.of();
    }
}
