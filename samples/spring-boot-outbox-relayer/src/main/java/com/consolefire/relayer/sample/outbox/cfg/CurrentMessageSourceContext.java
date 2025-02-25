package com.consolefire.relayer.sample.outbox.cfg;

import java.util.Optional;

public class CurrentMessageSourceContext {

    private static final CurrentMessageSourceContext CURRENT_TENANT_CONTEXT = new CurrentMessageSourceContext();
    private static final ThreadLocal<String> TENANT_THREAD_LOCAL = ThreadLocal.withInitial(() -> "application-tenant");

    public static void setCurrentTenant(String id) {
        TENANT_THREAD_LOCAL.set(id);
    }

    public static String getCurrentSourceId() {
        return Optional.ofNullable(TENANT_THREAD_LOCAL.get()).orElse("application-tenant");
    }

    public static void clearCurrentTenant() {
        TENANT_THREAD_LOCAL.set("application-tenant");
    }
}
