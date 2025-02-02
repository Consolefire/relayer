package com.consolefire.relayer.util.types;

import org.junit.jupiter.api.Test;

class TypeTokenTest {

    static class StringTypeToken extends TypeToken<String, String> {

    }

    static class One<T> extends TypeToken<T, String> {

    }

    static class Two<T, V> extends One<T> {

    }

    static class Three extends Two<Long, String> {

    }


    @Test
    void test1() {
        One<Long> stringTypeToken = new Three();
        System.out.println(stringTypeToken.getSourceType());
        System.out.println(stringTypeToken.getTargetType());

    }

}