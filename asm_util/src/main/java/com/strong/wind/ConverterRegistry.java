package com.strong.wind;

import lombok.Data;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 转换器注册中心 
public class ConverterRegistry {
    private static final Map<ClassPair, TypeConverter<?, ?>> converters = new ConcurrentHashMap<>();
 
    // 注册类型转换器
    public static <S, T> void register(Class<S> sourceType, Class<T> targetType, TypeConverter<S, T> converter) {
        converters.put(new  ClassPair(sourceType, targetType), converter);
    }
 
    // 获取类型转换器
    @SuppressWarnings("unchecked")
    public static <S, T> TypeConverter<S, T> getConverter(Class<S> sourceType, Class<T> targetType) {
        return (TypeConverter<S, T>) converters.get(new  ClassPair(sourceType, targetType));
    }

    @Getter
    // 类型对包装类
    private static class ClassPair {
        private final Class<?> source;
        private final Class<?> target;
 
        public ClassPair(Class<?> source, Class<?> target) {
            this.source  = source;
            this.target  = target;
        }
    }
}
