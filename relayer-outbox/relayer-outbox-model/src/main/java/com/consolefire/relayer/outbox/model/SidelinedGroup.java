package com.consolefire.relayer.outbox.model;

import com.consolefire.relayer.model.ParkedGroup;
import java.time.Instant;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SidelinedGroup extends ParkedGroup {

    @Builder
    public SidelinedGroup(String groupId, Instant createdAt, Instant updatedAt) {
        super(groupId, createdAt, updatedAt);
    }
}
