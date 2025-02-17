package com.consolefire.relayer.testutils.ext.handler;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.consolefire.relayer.testutils.logging.LoggerConstants;
import com.consolefire.relayer.testutils.logging.TestLogger;
import com.consolefire.relayer.testutils.logging.TestLoggerLoggingConfig;
import com.consolefire.relayer.testutils.logging.TestLoggerThreadLocal;
import com.consolefire.relayer.testutils.logging.TestLoggers;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstanceFactoryContext;
import org.junit.jupiter.api.extension.TestInstancePreConstructCallback;
import org.junit.jupiter.api.extension.TestInstancePreDestroyCallback;
import org.junit.platform.commons.PreconditionViolationException;
import org.slf4j.LoggerFactory;

public class TestLoggerExtensionHandler
    implements TestInstancePreConstructCallback, BeforeTestExecutionCallback,
    AfterTestExecutionCallback, TestInstancePreDestroyCallback {

    public static final String EXT_CTXT_KEY_LOGGER = "test-logger-context-key";
    private static final LoggerContext LOGGER_CONTEXT = (LoggerContext) LoggerFactory.getILoggerFactory();


    @Override
    public void preConstructTestInstance(TestInstanceFactoryContext factoryContext, ExtensionContext context)
        throws Exception {
        configureLoggerForClass(context);
    }

    @Override
    public void preDestroyTestInstance(ExtensionContext context) throws Exception {
        LOGGER_CONTEXT.reset();
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        configureLoggerForMethod(context);
    }


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        try {
            Method testMethod = context.getRequiredTestMethod();
            if (null != testMethod) {
                String methodName = testMethod.getName();
                resetLogLevels(TestLoggerThreadLocal.getTestLoggers(methodName));
            }
        } catch (PreconditionViolationException e) {

        }
    }

    private void configureLoggerForMethod(ExtensionContext context) {
        try {
            Method testMethod = context.getRequiredTestMethod();
            if (null != testMethod) {
                String methodName = testMethod.getName();
                List<TestLoggerLoggingConfig> loggersForTestClass = getLoggersForTestMethod(testMethod);
                TestLoggerThreadLocal.setTestLoggers(methodName, loggersForTestClass);
                setLogLevels(loggersForTestClass);
            }
        } catch (PreconditionViolationException e) {

        }
    }

    private static void configureLoggerForClass(ExtensionContext context) {
        Class<?> testClass = context.getRequiredTestClass();
        if (null != testClass) {
            String className = testClass.getCanonicalName();
            List<TestLoggerLoggingConfig> loggersForTestClass = getLoggersForTestClass(testClass);
            TestLoggerThreadLocal.setTestLoggers(className, loggersForTestClass);
            setLogLevels(loggersForTestClass);
        }
    }

    private static List<TestLoggerLoggingConfig> getLoggersForTestMethod(Method testMethod) {
        Map<String, TestLoggerLoggingConfig> loggerMap = new LinkedHashMap<>();
        if (testMethod.isAnnotationPresent(TestLogger.class)) {
            TestLogger testLogger = testMethod.getAnnotation(TestLogger.class);
            TestLoggerLoggingConfig loggingConfig = getTestLoggerLoggingConfigForMethod(testMethod,
                testLogger);
            loggerMap.put(testLogger.logger(), loggingConfig);
        }
        if (testMethod.isAnnotationPresent(TestLoggers.class)) {
            TestLoggers testLoggers = testMethod.getAnnotation(TestLoggers.class);
            Arrays.stream(testLoggers.loggers())
                .map(tl -> {
                    return getTestLoggerLoggingConfigForMethod(testMethod, tl);
                })
                .forEach(testLogger -> loggerMap.put(testLogger.getLoggerName(), testLogger));
        }
        return loggerMap.values().stream().toList();
    }

    private static TestLoggerLoggingConfig getTestLoggerLoggingConfigForMethod(Method testMethod,
        TestLogger testLogger) {
        String loggerName = testLogger.logger();
        if (LoggerConstants.NO_LOGGER_DEFINED.equals(testLogger.logger())) {
            loggerName = testMethod.getDeclaringClass().getName();
        }
        TestLoggerLoggingConfig loggingConfig = TestLoggerLoggingConfig.builder()
            .loggerName(loggerName).logLevel(testLogger.level())
            .build();
        return loggingConfig;
    }

    private static List<TestLoggerLoggingConfig> getLoggersForTestClass(Class<?> testClass) {
        Map<String, TestLoggerLoggingConfig> loggerMap = new LinkedHashMap<>();
        if (testClass.isAnnotationPresent(TestLogger.class)) {
            TestLogger testLogger = testClass.getAnnotation(TestLogger.class);
            TestLoggerLoggingConfig loggingConfig = getTestLoggerLoggingConfigForClass(testClass,
                testLogger);
            loggerMap.put(loggingConfig.getLoggerName(), loggingConfig);
        }
        if (testClass.isAnnotationPresent(TestLoggers.class)) {
            TestLoggers testLoggers = testClass.getAnnotation(TestLoggers.class);
            Arrays.stream(testLoggers.loggers())
                .map((tl) -> getTestLoggerLoggingConfigForClass(testClass, tl))
                .forEach(testLogger -> loggerMap.put(testLogger.getLoggerName(), testLogger));
        }
        return loggerMap.values().stream().toList();
    }

    private static TestLoggerLoggingConfig getTestLoggerLoggingConfigForClass(Class testClass, TestLogger testLogger) {
        String loggerName = testLogger.logger();
        if (LoggerConstants.NO_LOGGER_DEFINED.equals(loggerName)) {
            loggerName = testClass.getCanonicalName();
        }
        TestLoggerLoggingConfig loggingConfig = TestLoggerLoggingConfig.builder()
            .loggerName(loggerName).logLevel(testLogger.level())
            .build();
        return loggingConfig;
    }


    private static void setLogLevels(List<TestLoggerLoggingConfig> testLoggers) {
        testLoggers.forEach(TestLoggerExtensionHandler::setLogLevel);
    }

    private static void resetLogLevels(List<TestLoggerLoggingConfig> testLoggers) {
        testLoggers.forEach(TestLoggerExtensionHandler::resetLogLevel);
    }

    private static void resetLogLevel(TestLoggerLoggingConfig testLogger) {
        Logger logger = LOGGER_CONTEXT.getLogger(testLogger.getLoggerName());
        if (null != logger) {
            logger.setLevel(LOGGER_CONTEXT.getLogger(Logger.ROOT_LOGGER_NAME).getLevel());
        }
    }

    private static void setLogLevel(TestLoggerLoggingConfig testLogger) {
        Logger logger = LOGGER_CONTEXT.getLogger(testLogger.getLoggerName());
        if (null == logger) {
            LOGGER_CONTEXT.putProperty(testLogger.getLoggerName(), testLogger.getLogLevel().name());
        } else {
            logger.setLevel(Level.toLevel(testLogger.getLogLevel().name()));
        }
    }
}
