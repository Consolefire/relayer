package com.consolefire.relayer.testutils.ext.handler;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

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
