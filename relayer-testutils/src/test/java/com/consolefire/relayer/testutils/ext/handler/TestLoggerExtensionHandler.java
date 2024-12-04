package com.consolefire.relayer.testutils.ext.handler;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstanceFactoryContext;
import org.junit.jupiter.api.extension.TestInstancePreConstructCallback;
import org.slf4j.LoggerFactory;

public class TestLoggerExtensionHandler implements TestInstancePreConstructCallback {

    @Override
    public void preConstructTestInstance(TestInstanceFactoryContext factoryContext, ExtensionContext context)
        throws Exception {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // print logback's internal status
        StatusPrinter.print(lc);
    }
}
