package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.util.data.FilterProperties;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public interface GroupFilterProperties extends FilterProperties {


    String getGroupId();

    void setGroupId(String groupId);

    Set<String> getGroupIds();

    void setGroupIds(Set<String> groupId);

    @Getter
    @Setter
    @Builder
    class DefaultGroupFilterProperties
        extends DefaultFilterProperties
        implements GroupFilterProperties {

        private String groupId;
        private Set<String> groupIds;

    }
}
