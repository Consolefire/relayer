package com.consolefire.relayer.core.tasks;

import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageReaderTaskRunnable implements Runnable {

    private final AtomicBoolean runningState = new AtomicBoolean(false);

    private final MessageReaderTask messageReaderTask;


    public void stop() {
        runningState.compareAndSet(true, false);
    }

    public final boolean isRunning() {
        return runningState.get();
    }

    @Override
    public void run() {
        runningState.set(true);
        while (isRunning()) {
            messageReaderTask.execute();
        }

    }
}
