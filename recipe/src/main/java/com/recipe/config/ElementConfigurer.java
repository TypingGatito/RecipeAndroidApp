package com.recipe.config;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ElementConfigurer {

    private final Map<String, String> interfaceToClass = new HashMap<>();

    private final Map<String, Object> config = new HashMap<String, Object>();

    {
        interfaceToClass.put("", "");
    }

    public void configureObject(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();

        if (clazz.isAnnotationPresent(Element.class)) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : Arrays.stream(fields)
                    .filter(f -> f.isAnnotationPresent(Injected.class))
                    .toList()) {

                field.setAccessible(true);

                Class<?> fieldClazz = field.getType();

                if (config.containsKey(fieldClazz.getSimpleName())) {
                    field.set(obj, config.get(fieldClazz.getName()));
                    continue;
                }

                Object newInstance = initializeObject(fieldClazz);

                config.put(fieldClazz.getName(), newInstance);

                if (fieldClazz.isAnnotationPresent(Element.class)) configureObject(newInstance);

                field.set(obj, newInstance);
            }
        }
    }

    private Object initializeObject(Class<?> fieldClazz) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        Object newInstance;

        if (fieldClazz.isInterface()) {
            try {
                Class<?> classN = Class.forName(interfaceToClass.get(fieldClazz.getName()));
                newInstance = classN.getDeclaredConstructor().newInstance();
                return newInstance;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Method getInstanceMethod = fieldClazz.getMethod("getInstance");
            newInstance = getInstanceMethod.invoke(null);
        } catch (NoSuchMethodException e) {
            newInstance = fieldClazz.getDeclaredConstructor().newInstance();
        }

        return newInstance;
    }

}
