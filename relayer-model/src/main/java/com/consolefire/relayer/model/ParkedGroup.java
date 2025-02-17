package com.consolefire.relayer.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(asEnum = true)
public abstract class ParkedGroup {

    private String groupId;
    protected Instant createdAt;
    protected Instant updatedAt;

}
