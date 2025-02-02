package com.consolefire.relayer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestMessage extends Message<Long> {

    private int testCount;

}
