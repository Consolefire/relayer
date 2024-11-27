package com.consolefire.relayer.writer.interceptor;

import com.consolefire.relayer.model.Message;

import java.io.Serializable;

@FunctionalInterface
public interface BeforeWriteInterceptor<ID extends Serializable, M extends Message<ID>> {

    M before(M message);

}
