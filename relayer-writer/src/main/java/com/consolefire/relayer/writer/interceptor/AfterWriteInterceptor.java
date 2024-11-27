package com.consolefire.relayer.writer.interceptor;

import com.consolefire.relayer.model.Message;

import java.io.Serializable;

@FunctionalInterface
public interface AfterWriteInterceptor<ID extends Serializable, M extends Message<ID>> {

    M after(M message);

}
