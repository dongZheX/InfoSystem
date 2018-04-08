package com.dongzhex.jsonService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ASUS on 2018/3/10.
 */

public class ParameterizedTypeT implements ParameterizedType {
    //无法理解
    private final Class raw;
    private final Type[] args;
    public ParameterizedTypeT(Class raw, Type[] args) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
    }
    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }
    @Override
    public Type getRawType() {
        return raw;
    }
    @Override
    public Type getOwnerType() {return null;}
}
