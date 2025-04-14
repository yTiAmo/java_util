package com.strong.wind;

import org.objectweb.asm.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectConverter<S, T> {
    private final Class<S> sourceClass;
    private final Class<T> targetClass;
    private final Map<String, String> fieldMappings = new HashMap<>();
    private boolean convertNullFields = false;

    private ObjectConverter(Class<S> sourceClass, Class<T> targetClass) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    public static <S, T> ObjectConverter<S, T> create(Class<S> sourceClass, Class<T> targetClass) {
        return new ObjectConverter<>(sourceClass, targetClass);
    }

    public ObjectConverter<S, T> field(String sourceField, String targetField) {
        fieldMappings.put(sourceField, targetField);
        return this;
    }

    public ObjectConverter<S, T> convertNullFields(boolean convertNullFields) {
        this.convertNullFields = convertNullFields;
        return this;
    }

    public T convert(S source) throws Exception {
        T target = targetClass.getDeclaredConstructor().newInstance();

        // 使用 ASM 生成转换方法
        String methodName = "convert" + sourceClass.getSimpleName() + "To" + targetClass.getSimpleName();
        MethodVisitor mv = createMethodVisitor(methodName);

        // 遍历源类的字段
        for (Field sourceField : sourceClass.getDeclaredFields()) {
            sourceField.setAccessible(true);
            String sourceFieldName = sourceField.getName();
            String targetFieldName = fieldMappings.getOrDefault(sourceFieldName, sourceFieldName);

            try {
                Field targetField = targetClass.getDeclaredField(targetFieldName);
                targetField.setAccessible(true);
                Object value = sourceField.get(source);

                // 如果字段值为 null 且不转换 null 字段，则跳过
                if (value == null && !convertNullFields) {
                    continue;
                }

                // 使用 ASM 将值设置到目标对象的字段
                mv.visitVarInsn(Opcodes.ALOAD, 1); // 加载目标对象引用
                mv.visitVarInsn(Opcodes.ALOAD, 0); // 加载源对象引用
                mv.visitFieldInsn(Opcodes.GETFIELD, Type.getInternalName(sourceClass), sourceFieldName, Type.getDescriptor(sourceField.getType()));
                mv.visitFieldInsn(Opcodes.PUTFIELD, Type.getInternalName(targetClass), targetFieldName, Type.getDescriptor(targetField.getType()));
            } catch (NoSuchFieldException e) {
                // 目标类中没有对应的字段，跳过
            }
        }

        // 返回目标对象
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitInsn(Opcodes.ARETURN);
        mv.visitMaxs(3, 2); // 设置最大栈深度和局部变量表大小
        mv.visitEnd();

        return target;
    }

    private MethodVisitor createMethodVisitor(String methodName) {
        // 创建方法访问器
        // 初始化目标对象
        return new MethodVisitor(Opcodes.ASM9) {
            @Override
            public void visitCode() {
                super.visitCode();
                // 初始化目标对象
                mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(targetClass));
                mv.visitInsn(Opcodes.DUP);
                mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(targetClass), "<init>", "()V", false);
                mv.visitVarInsn(Opcodes.ASTORE, 1);
            }
        };
    }
}
