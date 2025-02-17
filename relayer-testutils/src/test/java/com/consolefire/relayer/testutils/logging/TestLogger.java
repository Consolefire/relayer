package com.consolefire.relayer.testutils.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.slf4j.event.Level;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface TestLogger {

    String logger() default LoggerConstants.NO_LOGGER_DEFINED;

    Level level() default Level.INFO;

}
