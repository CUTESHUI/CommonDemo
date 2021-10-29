package com.shui.utils.mongoHelper.reflection;

import java.io.Serializable;
import java.util.function.Function;

@FunctionalInterface
public interface SerializableFunction<E, R> extends Function<E, R>, Serializable {
  
}