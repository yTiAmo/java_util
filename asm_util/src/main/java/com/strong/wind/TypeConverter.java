package com.strong.wind;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 类型转换接口
public interface TypeConverter<S, T> {
    T convert(S source);
}
 
