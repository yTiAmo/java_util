package com.strong.wind;

import lombok.Data;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.util.ClassUtils.isAssignable;

public class ReflectConverter {
    private final Map<String, String> fieldMapping = new HashMap<>();
    private final List<Object> sources = new ArrayList<>();
    private boolean includeNull = false;
    private static final Map<Class<?>, List<PropertyDescriptor>> DESCRIPTOR_CACHE =
            new ConcurrentHashMap<>();

    // [1]() 改造字段获取方法（带缓存）
    private static Map<String, Field> getCachedFields(Class<?> clazz) {
        return FIELD_CACHE.computeIfAbsent(clazz,  k -> {
            Map<String, Field> fieldMap = new HashMap<>();
            // [8]() 递归获取类及父类字段
            for (Field field : clazz.getDeclaredFields())  {
                field.setAccessible(true);
                fieldMap.put(field.getName(),  field);
            }
            if (clazz.getSuperclass()  != null) {
                fieldMap.putAll(getCachedFields(clazz.getSuperclass()));
            }
            return Collections.unmodifiableMap(fieldMap);
        });
    }

    // [2]() 添加预加载机制（启动时预热常用类）
    public static void preloadCache(Class<?>... classes) {
        Arrays.stream(classes).forEach(clazz  -> {
            getCachedFields(clazz);
            CLASS_MAPPING_CACHE.putIfAbsent(
                    new ClassPair(clazz, Object.class),  Collections.emptyMap());
        });
    }

    // [3]() 添加缓存清理方法（应对动态类加载场景）
    public static void clearCache() {
        FIELD_CACHE.clear();
        CLASS_MAPPING_CACHE.clear();
    }
    // [4]() 使用ConcurrentHashMap保证线程安全
    private static final Map<Class<?>, Map<String, Field>> FIELD_CACHE =
            new ConcurrentHashMap<>(256);

    // [5]() 改造copyProperties方法
    private void copyProperties(Object source, Object target) {
        try {
            // 从缓存获取字段映射关系
            ClassPair key = new ClassPair(source.getClass(),  target.getClass());
            Map<String, String> mapping = CLASS_MAPPING_CACHE.getOrDefault(key,  fieldMapping);

            // 使用缓存字段数据
            Map<String, Field> fields = getCachedFields(source.getClass());
            for (Map.Entry<String, Field> entry : fields.entrySet()) {
                String srcName = entry.getKey();
                Field srcField = entry.getValue();
                Object value = srcField.get(source);
                if (!includeNull && value == null) return;

                // [7]() 优先使用缓存映射关系
                String targetName = mapping.getOrDefault(srcName,  srcName);
                Field targetField = getCachedFields(target.getClass()).get(targetName);

                if (targetField != null && isAssignable(value.getClass(), targetField.getType()))  {
                    targetField.set(target,  value);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // [6]() 新增类结构缓存（包含字段映射关系）
    private static final Map<ClassPair, Map<String, String>> CLASS_MAPPING_CACHE =
            new ConcurrentHashMap<>(128);


    // 链式配置方法
    public ReflectConverter mapField(String sourceField, String targetField) {
        fieldMapping.put(sourceField,  targetField);
        return this;
    }
 
    public ReflectConverter addSource(Object source) {
        sources.add(source); 
        return this;
    }
 
    public ReflectConverter includeNullValues(boolean flag) {
        includeNull = flag;
        return this;
    }
    public <S, T> ReflectConverter registerConverter(Class<S> sourceType, Class<T> targetType, TypeConverter<S, T> converter) {
        ConverterRegistry.register(sourceType,  targetType, converter);
        return this;
    }
 
    // 转换执行方法 
    public <T> T convert(Class<T> targetClass) throws Exception {
        T target = targetClass.newInstance(); 
        for (Object source : sources) {
            copyProperties(source, target);
        }
        return target;
    }
    @Data
    // 新增ClassPair缓存Key对象
    private static class ClassPair {
        final Class<?> source;
        final Class<?> target;

        private ClassPair(Class<?> source, Class<?> target) {
            this.source = source;
            this.target = target;
        }

    }
}