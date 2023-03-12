package org.example.configs.utils;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.Function;

public class Lambdas {

    @SuppressWarnings("unchecked")
    public static <E, T> Function<E, T> provideGetter(String fieldName, Class<T> targetType, Class<E> sourceType) {
        String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType type = MethodType.methodType(targetType);
            MethodHandle virtual = lookup.findVirtual(sourceType, getterName, type);
            CallSite callSite = LambdaMetafactory.metafactory(lookup,
                    "apply",
                    MethodType.methodType(Function.class),
                    MethodType.methodType(Object.class, Object.class),
                    virtual, MethodType.methodType(targetType, sourceType)
            );
            return (Function<E, T>) callSite.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
