package com.consolefire.relayer.core.checkpoint.task;

public record CheckpointCompletedEvent(String sourceIdentifier, boolean completed) {

    @Override
    public String toString() {
        return "CheckpointCompletedEvent{" +
            "sourceIdentifier='" + sourceIdentifier + '\'' +
            ", completed=" + completed +
            '}';
    }
}
