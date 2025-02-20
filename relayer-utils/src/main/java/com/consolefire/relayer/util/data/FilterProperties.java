package com.consolefire.relayer.util.data;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

public interface FilterProperties {

    Instant getCreatedBefore();

    void setCreatedBefore(Instant createdBefore);

    Instant getCreatedAt();

    void setCreatedAt(Instant createdAt);

    Instant getCreatedAfter();

    void setCreatedAfter(Instant createdAfter);

    Long getLimit();

    void setLimit(Long limit);


    @Getter
    @Setter
    class DefaultFilterProperties implements FilterProperties {

        private Instant createdBefore;
        private Instant createdAt;
        private Instant createdAfter;
        private Long limit;
    }
}
