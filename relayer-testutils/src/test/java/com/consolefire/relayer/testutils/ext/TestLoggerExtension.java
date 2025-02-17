package com.consolefire.relayer.testutils.ext;

import com.consolefire.relayer.testutils.ext.handler.TestLoggerExtensionHandler;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(TestLoggerExtensionHandler.class)
public @interface TestLoggerExtension {

}
