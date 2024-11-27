package com.consolefire.relayer.testutils.data;

import com.consolefire.relayer.testutils.ext.handler.CreateTableExtensionHandler;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Order(2)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(CreateTableExtensionHandler.class)
public @interface CreateTableExtension {

    String statement() default "";

    String[] statements() default "";

    String script() default "";

    String[] scripts() default "";
}
