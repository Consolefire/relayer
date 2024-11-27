package com.consolefire.relayer.model;

import lombok.*;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(asEnum = true)
public class SidelinedGroup {

    private String groupId;

}
