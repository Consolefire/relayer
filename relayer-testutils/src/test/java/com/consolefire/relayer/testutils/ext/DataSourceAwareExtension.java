package com.consolefire.relayer.testutils.ext;

import com.consolefire.relayer.testutils.ext.handler.TestDataSourceExtensionHandler;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Order(1)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TestDataSourceExtensionHandler.class)
public @interface DataSourceAwareExtension {
}
