package com.consolefire.relayer.testutils.logging;

import ch.qos.logback.classic.BasicConfigurator;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;

public class LogbackConfigurator extends BasicConfigurator {

    @Override
    public ExecutionStatus configure(LoggerContext loggerContext) {
        loggerContext.setName("TEST-LOGGER-CONTEXT");
        loggerContext.reset();
        setContext(loggerContext);
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(setupConsoleAppender());
        rootLogger.setLevel(Level.toLevel(LoggerThreadLocal.getLogLevel(Logger.ROOT_LOGGER_NAME).name(), Level.ERROR));
        return ExecutionStatus.DO_NOT_INVOKE_NEXT_IF_ANY;
    }

    private ConsoleAppender setupConsoleAppender() {
        ConsoleAppender appender = new ConsoleAppender();
        appender.setContext(context);
        appender.setName("CONSOLE");

        // Configure component of type PatternLayoutEncoder
        PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
        patternLayoutEncoder.setContext(context);
        patternLayoutEncoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36}: -%kvp- %msg%n");
        patternLayoutEncoder.setParent(appender);
        patternLayoutEncoder.start();
        // Inject component of type PatternLayoutEncoder into parent
        appender.setEncoder(patternLayoutEncoder);

        appender.start();
        return appender;
    }
}
