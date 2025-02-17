package com.consolefire.relayer.testutils.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TestLoggerThreadLocal extends ThreadLocal<Map<String, List<TestLoggerLoggingConfig>>> {

    private static final TestLoggerThreadLocal INSTANCE = new TestLoggerThreadLocal();

    private TestLoggerThreadLocal() {

    }

    public static TestLoggerThreadLocal getInstance() {
        return INSTANCE;
    }

    public static List<TestLoggerLoggingConfig> getTestLoggers(String key) {
        Map<String, List<TestLoggerLoggingConfig>> loggerMap = getInstance().get();
        if (null != loggerMap) {
            return Optional.ofNullable(loggerMap.get(key)).orElse(new ArrayList<>());
        }
        return new ArrayList<>();
    }

    public static void setTestLoggers(String key, List<TestLoggerLoggingConfig> testLoggers) {
        Map<String, List<TestLoggerLoggingConfig>> loggerMap = getInstance().get();
        if (null != loggerMap) {
            loggerMap.putIfAbsent(key, testLoggers);
        } else {
            withInitial(() -> Map.of(key, testLoggers));
        }
    }

    @Override
    protected Map<String, List<TestLoggerLoggingConfig>> initialValue() {
        return new ConcurrentHashMap<>();
    }


}
