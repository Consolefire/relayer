package com.consolefire.relayer.core.reader.flow.fns;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;

import java.util.function.Function;

@FunctionalInterface
public interface ReaderCheckpointValidator extends Function<ReaderCheckpoint, Boolean> {

}
