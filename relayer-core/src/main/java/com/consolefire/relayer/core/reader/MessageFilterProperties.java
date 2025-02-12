package com.consolefire.relayer.core.reader;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

public interface MessageFilterProperties<ID extends Serializable> {

    long DEFAULT_LIMIT = 1;
    int MIN_ATTEMPT_COUNT = 0;
    int DEFAULT_MAX_ATTEMPT_COUNT = 3;

    MessageFilterProperties<? extends Serializable> DEFAULT = DefaultMessageFilterProperties.getDefault();

    ID getMessageId();

    void setMessageId(ID messageId);

    Set<ID> getMessageIds();

    void setMessageIds(Set<ID> messageIds);

    String getGroupId();

    void setGroupId(String groupId);

    Long getStartSequence();

    void setStartSequence(Long startSequence);

    boolean isStartSequenceInclusive();

    void setStartSequenceInclusive(boolean startSequenceInclusive);

    Long getEndSequence();

    void setEndSequence(Long endSequence);

    boolean isEndSequenceInclusive();

    void setEndSequenceInclusive(boolean endSequenceInclusive);

    Instant getCreatedBefore();

    void setCreatedBefore(Instant createdBefore);

    Instant getCreatedAt();

    void setCreatedAt(Instant createdAt);

    Instant getCreatedAfter();

    void setCreatedAfter(Instant createdAfter);

    Instant getAttemptedBefore();

    void setAttemptedBefore(Instant attemptedBefore);

    Instant getAttemptedAt();

    void setAttemptedAt(Instant attemptedAt);

    Instant getAttemptedAfter();

    void setAttemptedAfter(Instant attemptedAfter);

    Integer getMaxAttemptCount();

    void setMaxAttemptCount(Integer maxAttemptCount);

    Long getLimit();

    void setLimit(Long limit);


    @Getter
    @Setter
    class DefaultMessageFilterProperties<ID extends Serializable>
        implements MessageFilterProperties<ID> {

        private ID messageId;
        private Set<ID> messageIds;
        private String groupId;
        private Long startSequence;
        private Long endSequence;
        private boolean startSequenceInclusive;
        private boolean endSequenceInclusive;
        private Instant createdBefore;
        private Instant createdAt;
        private Instant createdAfter;
        private Instant attemptedBefore;
        private Instant attemptedAt;
        private Instant attemptedAfter;
        private Integer maxAttemptCount;
        private Long limit;

        public static <I extends Serializable> DefaultMessageFilterProperties<I> getDefault() {
            DefaultMessageFilterProperties<I> props = new DefaultMessageFilterProperties<>();
            props.setLimit(DEFAULT_LIMIT);
            props.setMaxAttemptCount(DEFAULT_MAX_ATTEMPT_COUNT);
            return props;
        }
    }
}
