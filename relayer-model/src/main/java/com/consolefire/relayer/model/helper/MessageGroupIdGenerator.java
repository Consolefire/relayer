package com.consolefire.relayer.model.helper;

public interface MessageGroupIdGenerator<PAYLOAD, META> {

    String generate(PAYLOAD payload, META metadata);

}
