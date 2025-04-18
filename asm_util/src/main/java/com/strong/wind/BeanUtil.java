package com.strong.wind;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.springframework.util.TypeUtils.isAssignable;

public class BeanUtil {
    private static final Map<Class<?>, Function<Object,Object>> CONVERTERS = new HashMap<>();

    static {
        // 基础类型注册
        registerConverter(Boolean.class, BeanUtil::parseBoole);
        registerConverter(boolean.class, BeanUtil::parseBoole);

        registerConverter(Integer.class, BeanUtil::parseInt);
        registerConverter(int.class, BeanUtil::parseInt);

        registerConverter(Long.class, BeanUtil::parseLong);
        registerConverter(long.class, BeanUtil::parseLong);

        registerConverter(Float.class, BeanUtil::parseFloat);
        registerConverter(float.class, BeanUtil::parseFloat);

        registerConverter(Double.class, BeanUtil::parseDouble);
        registerConverter(double.class, BeanUtil::parseDouble);

        registerConverter(String.class, BeanUtil::parseString);
        registerConverter(Character.class, BeanUtil::parseChar);
        registerConverter(char.class, BeanUtil::parseChar);

        registerConverter(BigInteger.class, v -> new BigInteger(v.toString()));
        registerConverter(BigDecimal.class,v -> new BigDecimal(v.toString()));
        registerConverter(LocalDate.class, v -> LocalDate.parse(v.toString()));
        registerConverter(LocalDateTime.class, v -> LocalDateTime.parse(v.toString()));
    }
    private static Object parseBoole(Object o) {
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        if (o instanceof String) {
            String s = ((String) o).trim().toUpperCase();
            if (s.equals("1")) {
                return true;
            }
            if (s.equals("0")) {
                return false;
            }
            return Boolean.getBoolean(s);
        }
        throw new IllegalArgumentException("无效的布尔值");
    }
    // 整数类型
    private static Object parseInt(Object o) {
        if (o instanceof Integer) return o;
        if (o instanceof String) {
            try {
                return Integer.parseInt(((String) o).trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("无效的整数值: " + o);
            }
        }
        if (o instanceof Number) return ((Number) o).intValue();
        throw new IllegalArgumentException("无法转换为Integer类型");
    }

    // 长整型
    private static Object parseLong(Object o) {
        if (o instanceof Long) return o;
        if (o instanceof String) {
            try {
                return Long.parseLong(((String) o).trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("无效的长整型值: " + o);
            }
        }
        if (o instanceof Number) return ((Number) o).longValue();
        throw new IllegalArgumentException("无法转换为Long类型");
    }

    // 浮点型
    private static Object parseFloat(Object o) {
        if (o instanceof Float) return o;
        if (o instanceof String) {
            try {
                return Float.parseFloat(((String) o).trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("无效的浮点型值: " + o);
            }
        }
        if (o instanceof Number) return ((Number) o).floatValue();
        throw new IllegalArgumentException("无法转换为Float类型");
    }

    // 双精度浮点型
    private static Object parseDouble(Object o) {
        if (o instanceof Double) return o;
        if (o instanceof String) {
            try {
                return Double.parseDouble(((String) o).trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("无效的双精度浮点型值: " + o);
            }
        }
        if (o instanceof Number) return ((Number) o).doubleValue();
        throw new IllegalArgumentException("无法转换为Double类型");
    }

    // 字符串类型（直接返回原值）
    private static Object parseString(Object o) {
        return o != null ? o.toString() : null;
    }

    // 字符类型（处理单字符或字符串）
    private static Object parseChar(Object o) {
        if (o instanceof Character) return o;
        if (o instanceof String) {
            String s = ((String) o).trim();
            if (s.length() == 1) return s.charAt(0);
            throw new IllegalArgumentException("字符串长度必须为1: " + s);
        }
        if (o instanceof Number) return (char) ((Number) o).intValue();
        throw new IllegalArgumentException("无法转换为Character类型");
    }

    private static <E extends Enum<E>> E parseEnum(Class<?> enumType,Object value){
        String s = value.toString().trim();
        return Enum.valueOf((Class<E>)enumType,s);
    }
    public static void registerConverter(Class<?> targetType, Function<Object,Object> converter){
        CONVERTERS.put(targetType, converter);
    }

    public static Object getProperty(Object bean,String field){
        try{
            Optional<Method> getter = findGetter(bean.getClass(), field);
            if (getter.isPresent()) {
                return getter.get().invoke(bean);
            }
            return forceFetField(bean,field);
        }catch (Exception e){
            throw new RuntimeException("获取属性失败："+field,e);
        }
    }

    public static void setProperty(Object bean,String field,Object value){
        try{
            Optional<Method> setter = findSetter(bean.getClass(), field, value != null ? value.getClass() : null);
            if(setter.isPresent()){
                setter.get().invoke(bean,coverType(setter.get().getParameterTypes()[0],value));
                return;
            }
            forceSetField(bean,field,value);
        }catch (Exception e){
            throw new RuntimeException("设置属性失败："+field,e);
        }
    }

    private static Object coverType(Class<?> parameterType, Object value) {
        if (value == null){
            if (parameterType.isPrimitive()) {
                throw new IllegalArgumentException("不能给null赋值基本类型");
            }
            return null;
        }
        if (parameterType.isAssignableFrom(value.getClass())) {
            return value;
        }
        Function<Object, Object> function = CONVERTERS.get(parameterType);
        if (function != null) {
            return function.apply(value);
        }

        if (parameterType.isEnum()) {
            return parseEnum(parameterType,value);
        }

        try {
            return parameterType.getConstructor(value.getClass()).newInstance(value);
        }catch (Exception ignored){}
        throw new IllegalArgumentException("无法将类型"+value.getClass().getName()+"转换成"+parameterType.getName());
    }


    private static Object forceFetField(Object bean, String field) throws NoSuchFieldException, IllegalAccessException {
        if (StringUtils.isEmpty(field)) {
            return null;
        }
        Field declaredField = bean.getClass().getDeclaredField(field);
        declaredField.setAccessible(true);
        return declaredField.get(bean);
    }

    private static Optional<Method> findSetter(Class<?> clazz,String propertyName,Class<?> valueType){
        String setterName = "set"+capitalize(propertyName);
        return Arrays.stream(clazz.getMethods())
                .filter(m->m.getName().equals(setterName))
                .filter(m-> m.getParameterCount() ==1)
                .filter(m-> isAssignable(m.getParameterTypes()[0],valueType))
                .findFirst();
    }
    private static Optional<Method> findGetter(Class<?> clazz,String propertyName){
        String [] possibleGetters = {
                "get"+capitalize(propertyName),
                "is"+capitalize(propertyName),
                propertyName
        };
        return Arrays.stream(possibleGetters)
                .flatMap(name ->{
                    try{
                        Method method = clazz.getMethod(name);
                        return Stream.of(method);
                    }catch (Exception e){
                        return Stream.empty();
                    }
                })
                .filter(m-> m.getParameterCount() == 0)
                .findFirst();
    }

    private static void forceSetField(Object bean,String fieldName,Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = bean.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(bean,value);
    }
    private static Object forceGetField(Object bean,String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (StringUtils.isEmpty(fieldName)) {
            return null;
        }
        Field field = bean.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(bean);
    }

    private static String capitalize(String str){
        if(str == null || str.isEmpty()){
            return str;
        }
        if(str.length()>=2 && Character.isUpperCase(str.charAt(1))){
            return str;
        }
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }
}
