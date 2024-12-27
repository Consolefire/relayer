package com.consolefire.relayer.util;

import java.io.Serializable;
import java.util.TreeMap;

public class ConsistentHashMap<K extends Serializable> {

    private final int numberOfReplicas;
    private final TreeMap<Long, K> hashRing = new TreeMap<>();


}
