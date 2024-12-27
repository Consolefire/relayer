package com.consolefire.relayer.util;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerQueue<E> {

    private final ConcurrentLinkedQueue<E> targetQueue = new ConcurrentLinkedQueue<>();
    @Getter
    private final Object fullLock = new Object();
    private final Object emptyLock = new Object();
    private final Object pauseResumeLock = new Object();


    public final void waitOnEmpty() {
        synchronized (emptyLock) {
            try {
                emptyLock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public final void notifyNotEmpty() {
        synchronized (emptyLock) {
            emptyLock.notify();
        }
    }

    public final void waitOnFull() {
        synchronized (fullLock) {
            try {
                fullLock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public final void notifyNotFull() {
        synchronized (fullLock) {
            fullLock.notify();
        }
    }

    public final void pause() {
        synchronized (pauseResumeLock) {
            try {
                pauseResumeLock.wait();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public final void resume() {
        synchronized (pauseResumeLock) {
            pauseResumeLock.notify();
        }
    }

    public final int size() {
        return targetQueue.size();
    }

    public final boolean isEmpty() {
        return targetQueue.isEmpty();
    }

    public final boolean add(E element) {
        log.debug("Adding element: {}", element);
        if (null == element) {
            log.warn("Not adding null element");
            return false;
        }

        boolean result = targetQueue.add(element);
        log.debug("Element {} to queue", result ? "added" : "not added");
        if (result) {
            log.debug("Notifying that the queue is not empty");
            notifyNotEmpty();
        }
        return result;
    }

    public final E poll() {
        log.debug("Polling element from queue");
        E element = targetQueue.poll();
        log.debug("Notifying that the queue is not full");
        notifyNotFull();
        return element;
    }

    public final E peek() {
        log.debug("Peeking element from queue");
        return targetQueue.peek();
    }

    public final List<E> peekAll() {
        return targetQueue.stream().toList();
    }

    public final Iterator<E> iterator() {
        return targetQueue.iterator();
    }

    public void clear() {
        targetQueue.clear();
    }
}
