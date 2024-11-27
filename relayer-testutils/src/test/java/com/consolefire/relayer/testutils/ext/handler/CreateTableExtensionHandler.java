package com.consolefire.relayer.testutils.ext.handler;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CreateTableExtensionHandler implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        Class<?> testClass = extensionContext.getRequiredTestClass();
        assertNotNull(testClass);
        Optional<Object> testInstance = extensionContext.getTestInstance();
        assertAll(
                () -> assertNotNull(testInstance),
                () -> assertTrue(testInstance.isPresent())
        );
    }
}
