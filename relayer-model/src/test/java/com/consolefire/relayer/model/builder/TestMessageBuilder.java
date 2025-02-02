package com.consolefire.relayer.model.builder;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.TestMessage;
import com.consolefire.relayer.model.TestMessagePayload;
import java.util.Map;
import lombok.NonNull;

public class TestMessageBuilder
    extends
    MessageBuilder<Long, TestMessagePayload, Map<String, String>, Map<String, Object>, TestMessage, TestMessageBuilder> {

    private int testCount;

    public TestMessageBuilder withTestCount(int x) {
        this.testCount = x;
        return this;
    }

    @Override
    protected TestMessageBuilder self() {
        return this;
    }

    @Override
    protected @NonNull TestMessage initMessage() {
        return new TestMessage();
    }

    @Override
    protected void setAdditionalProperties(Message<Long> message) {
        if (message instanceof TestMessage testMessage) {
            testMessage.setTestCount(testCount);
        }
    }
}
