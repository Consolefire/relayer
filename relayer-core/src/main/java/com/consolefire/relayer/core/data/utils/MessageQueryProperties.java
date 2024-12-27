package com.consolefire.relayer.core.data.utils;

import com.consolefire.relayer.core.data.query.SelectQuery;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.With;


@With
@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class MessageQueryProperties<ID extends Serializable> {

    private final ID messageId;
    private final Set<ID> messageIdSet;
    private final String groupId;
    private final Set<String> groupIdSet;
    private final int limit;
    private final Instant createdBefore;
    private final Instant createdAt;
    private final Instant createdAfter;

    public SelectQuery toSelectQuery() {
        // todo: implement
        return null;
    }
}
