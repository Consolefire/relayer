package com.consolefire.relayer.sample.outbox;

import java.util.Optional;

public class CurrentTenantContext {

    private static final CurrentTenantContext CURRENT_TENANT_CONTEXT = new CurrentTenantContext();
    private static final ThreadLocal<String> TENANT_THREAD_LOCAL = ThreadLocal.withInitial(() -> "application-tenant");

    public static void setCurrentTenant(String id) {
        TENANT_THREAD_LOCAL.set(id);
    }

    public static String getCurrentTenantId() {
        return Optional.ofNullable(TENANT_THREAD_LOCAL.get()).orElse("application-tenant");
    }

    public static void clearCurrentTenant() {
        TENANT_THREAD_LOCAL.set("application-tenant");
    }
}
