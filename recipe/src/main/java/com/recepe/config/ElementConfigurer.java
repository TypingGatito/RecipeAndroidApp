package com.recepe.config;

import com.recepe.annotations.Element;
import com.recepe.annotations.Injected;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ElementConfigurer {

    private final Map<String, String> interfaceToClass = new HashMap<>();

    private final Map<String, Object> config = new HashMap<String, Object>();

    public void configureObject(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();

        if (clazz.isAnnotationPresent(Element.class)) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (!field.isAnnotationPresent(Injected.class)) {
                    continue;
                }

                field.setAccessible(true);

                Class<?> fieldClazz = field.getType();

                if (config.containsKey(fieldClazz.getSimpleName())) {
                    field.set(obj, config.get(fieldClazz.getName()));
                    continue;
                }

                Object newInstance;
                try {
                    Method getInstanceMethod = fieldClazz.getMethod("getInstance");
                    newInstance = getInstanceMethod.invoke(null);
                } catch (NoSuchMethodException e) {
                    newInstance = fieldClazz.getDeclaredConstructor().newInstance();
                }

                config.put(fieldClazz.getName(), newInstance);

                if (fieldClazz.isAnnotationPresent(Element.class)) configureObject(newInstance);

                field.set(obj, newInstance);
            }
        }
    }

}
