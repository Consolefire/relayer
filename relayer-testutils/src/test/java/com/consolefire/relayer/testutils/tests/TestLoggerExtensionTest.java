package com.consolefire.relayer.testutils.tests;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.qos.logback.classic.LoggerContext;
import com.consolefire.relayer.testutils.ext.TestLoggerExtension;
import com.consolefire.relayer.testutils.logging.TestLogger;
import com.consolefire.relayer.testutils.logging.TestLoggers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;


@TestLoggerExtension
@TestLoggers(
    loggers = {
        @TestLogger(logger = "com.consolefire.relayer", level = Level.ERROR),
        @TestLogger(logger = "com.consolefire.relayer.testutils", level = Level.INFO)
    }
)
public class TestLoggerExtensionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLoggerExtensionTest.class);

    @BeforeAll
    public static void beforeAll() {
        printLogLevels();
    }

    private static void printLogLevels() {
        String messageFormat = "Logger: [%s] with level: %s";
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLoggerList().forEach(logger -> {
            System.out.println(format(messageFormat, logger.getName(), logger.getLevel()));
        });
    }

    @Test
    @TestLogger(logger = "com.consolefire.relayer.testutils", level = Level.TRACE)
    public void testMethodWithTestLoggerAnnotationTrace() {
        printLogLevels();
        assertAll(
            () -> assertTrue(LOGGER.isTraceEnabled()),
            () -> assertTrue(LOGGER.isDebugEnabled()),
            () -> assertTrue(LOGGER.isInfoEnabled()),
            () -> assertTrue(LOGGER.isWarnEnabled()),
            () -> assertTrue(LOGGER.isErrorEnabled())
        );
    }

    @Test
    @TestLogger(logger = "com.consolefire.relayer.testutils", level = Level.DEBUG)
    public void testMethodWithTestLoggerAnnotationDebug() {
        assertAll(
            () -> assertFalse(LOGGER.isTraceEnabled()),
            () -> assertTrue(LOGGER.isDebugEnabled()),
            () -> assertTrue(LOGGER.isInfoEnabled()),
            () -> assertTrue(LOGGER.isWarnEnabled()),
            () -> assertTrue(LOGGER.isErrorEnabled())
        );
    }

    @Test
    @TestLogger(logger = "com.consolefire.relayer.testutils", level = Level.INFO)
    public void testMethodWithTestLoggerAnnotationInfo() {
        assertAll(
            () -> assertFalse(LOGGER.isTraceEnabled()),
            () -> assertFalse(LOGGER.isDebugEnabled()),
            () -> assertTrue(LOGGER.isInfoEnabled()),
            () -> assertTrue(LOGGER.isWarnEnabled()),
            () -> assertTrue(LOGGER.isErrorEnabled())
        );
    }

    @Test
    @TestLogger(logger = "com.consolefire.relayer.testutils", level = Level.WARN)
    public void testMethodWithTestLoggerAnnotationWarn() {
        assertAll(
            () -> assertFalse(LOGGER.isTraceEnabled()),
            () -> assertFalse(LOGGER.isDebugEnabled()),
            () -> assertFalse(LOGGER.isInfoEnabled()),
            () -> assertTrue(LOGGER.isWarnEnabled()),
            () -> assertTrue(LOGGER.isErrorEnabled())
        );
    }

    @Test
    @TestLogger(logger = "com.consolefire.relayer.testutils", level = Level.ERROR)
    public void testMethodWithTestLoggerAnnotationError() {
        assertAll(
            () -> assertFalse(LOGGER.isTraceEnabled()),
            () -> assertFalse(LOGGER.isDebugEnabled()),
            () -> assertFalse(LOGGER.isInfoEnabled()),
            () -> assertFalse(LOGGER.isWarnEnabled()),
            () -> assertTrue(LOGGER.isErrorEnabled())
        );
    }

    @Test
    @TestLogger(level = Level.ERROR)
    public void testMethodWithTestLoggerAnnotationError_CurrentMethod() {
        printLogLevels();
        assertAll(
            () -> assertFalse(LOGGER.isTraceEnabled()),
            () -> assertFalse(LOGGER.isDebugEnabled()),
            () -> assertFalse(LOGGER.isInfoEnabled()),
            () -> assertFalse(LOGGER.isWarnEnabled()),
            () -> assertTrue(LOGGER.isErrorEnabled())
        );
    }

    @Test
    @TestLogger
    public void testMethodWithTestLoggerAnnotationDefault_CurrentMethod() {
        printLogLevels();
        assertAll(
            () -> assertFalse(LOGGER.isTraceEnabled()),
            () -> assertFalse(LOGGER.isDebugEnabled()),
            () -> assertTrue(LOGGER.isInfoEnabled()),
            () -> assertTrue(LOGGER.isWarnEnabled()),
            () -> assertTrue(LOGGER.isErrorEnabled())
        );
    }

    @Test
    @TestLoggers(
        loggers = {
            @TestLogger(logger = "com.consolefire.relayer", level = Level.INFO),
            @TestLogger(logger = "com.consolefire.relayer.testutils", level = Level.ERROR)
        }
    )
    public void testMethodWithDifferentTestLoggersAnnotation() {
        assertAll(
            () -> assertFalse(LOGGER.isTraceEnabled()),
            () -> assertFalse(LOGGER.isDebugEnabled()),
            () -> assertFalse(LOGGER.isInfoEnabled()),
            () -> assertFalse(LOGGER.isWarnEnabled()),
            () -> assertTrue(LOGGER.isErrorEnabled())
        );
    }
}
