package com.consolefire.relayer.util.types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import lombok.Getter;

@Getter
public abstract class TypeToken<S, T> {

    private Type sourceType;
    private Type targetType;

    protected TypeToken() {
        Type superClass = getClass().getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) superClass).getActualTypeArguments();
        if(actualTypeArguments.length == 2){
            sourceType = actualTypeArguments[0];
            targetType = actualTypeArguments[1];
        }
    }


}