package com.consolefire.relayer.outbox.core.reader;

import com.consolefire.relayer.core.reader.MessageFilterProperties;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.util.data.DataSourceResolver;
import com.consolefire.relayer.util.data.PreparedStatementSetter;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultSidelinedGroupReader implements SidelinedGroupReader {

    private final DataSourceResolver dataSourceResolver;
    private final SidelinedGroupReadQueryProvider sidelinedGroupReadQueryProvider;


    @Override
    public Set<String> getAllGroups(MessageSourceProperties messageSourceProperties) {
        return Set.of();
    }
}
