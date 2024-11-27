package com.consolefire.relayer.testutils.ext.handler;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.platform.commons.support.AnnotationSupport.findAnnotatedFields;

import com.consolefire.relayer.testutils.data.H2InMemoryDataSourceBuilder;
import com.consolefire.relayer.testutils.data.TestDataSource;
import com.consolefire.relayer.testutils.data.TestDataSourceBuilder;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javax.sql.DataSource;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.ModifierSupport;

public class TestDataSourceExtensionHandler
    implements BeforeAllCallback, ParameterResolver {

    public static final String EXT_CTXT_KEY_DATA_SOURCE = "test-data-source-context-key";

    private final TestDataSourceBuilder dataSourceBuilder = new H2InMemoryDataSourceBuilder();


    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        injectDataSource(extensionContext, ModifierSupport::isNotStatic);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
        throws ParameterResolutionException {
        return parameterContext.isAnnotated(TestDataSource.class)
            && isDataSource(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
        throws ParameterResolutionException {
        return getOrBuildDataSource(extensionContext);
    }

    private DataSource getOrBuildDataSource(ExtensionContext extensionContext) {
        ExtensionContext rootContext = extensionContext.getRoot();
        ExtensionContext.Store store = rootContext.getStore(ExtensionContext.Namespace.GLOBAL);
        return store.getOrComputeIfAbsent(EXT_CTXT_KEY_DATA_SOURCE, __ -> dataSourceBuilder.buildDataSource(),
            DataSource.class);
    }

    private void injectDataSource(ExtensionContext extensionContext,
        Predicate<Field> predicate) {
        predicate = predicate.and(field -> DataSource.class.getTypeName().equals(field.getType().getTypeName()));

        Class<?> testClass = extensionContext.getRequiredTestClass();
        assertNotNull(testClass);
        Optional<Object> testInstance = extensionContext.getTestInstance();
        assertAll(
            () -> assertNotNull(testInstance),
            () -> assertTrue(testInstance.isPresent())
        );

        List<Field> annotatedFields = findAnnotatedFields(testClass, TestDataSource.class, predicate);
        annotatedFields.forEach(field -> {
            try {
                field.setAccessible(true);
                DataSource dataSource = getOrBuildDataSource(extensionContext);
                field.set(testInstance.get(), dataSource);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private static boolean isDataSource(Class<?> type) {
        return type == DataSource.class;
    }

}
