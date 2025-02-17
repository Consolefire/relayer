package com.consolefire.relayer.testutils.logging;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.event.Level;

public class LoggerThreadLocal {

    private static final ThreadLocal<Map<String, Level>> LOG_LEVEL = ThreadLocal.withInitial(() -> defaultLevels());

    private static ConcurrentHashMap<String, Level> defaultLevels() {
        ConcurrentHashMap<String, Level> map = new ConcurrentHashMap<>();
        map.put(Logger.ROOT_LOGGER_NAME, Level.ERROR);
        map.put("com.consolefire.relayer", Level.ERROR);
        return map;
    }

    public static void setLogLevel(String logger, Level level) {
        Map<String, Level> stringLevelMap = LOG_LEVEL.get();
        if (null != stringLevelMap) {
            stringLevelMap = new HashMap<>();
            LOG_LEVEL.set(stringLevelMap);
        }
        LOG_LEVEL.get().put(logger, level);
    }

    public static Level getLogLevel(String rootLoggerName) {
        if (null == LOG_LEVEL.get()) {
            return defaultLevels().get(rootLoggerName);
        }
        return LOG_LEVEL.get().get(rootLoggerName);
    }
}
