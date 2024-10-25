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
        interfaceToClass.put("com.recipe.repositories.IAdminRepository", "com.recipe.in_memory.repositories.AdminRepository");
        interfaceToClass.put("com.recipe.repositories.ICommentaryRepository", "com.recipe.in_memory.repositories.CommentaryRepository");
        interfaceToClass.put("com.recipe.repositories.IIngredientRepository", "com.recipe.in_memory.repositories.IngredientRepository");
        interfaceToClass.put("com.recipe.repositories.IRatingRepository", "com.recipe.in_memory.repositories.RatingRepository");
        interfaceToClass.put("com.recipe.repositories.IRecipeRepository", "com.recipe.in_memory.repositories.RecipeRepository");
        interfaceToClass.put("com.recipe.repositories.ISectionRepository", "com.recipe.in_memory.repositories.SectionRepository");
        interfaceToClass.put("com.recipe.repositories.IUserRepository", "com.recipe.in_memory.repositories.UserRepository");
        interfaceToClass.put("com.recipe.repositories.IStepRepository", "com.recipe.in_memory.repositories.StepRepository");
    }

    public void configureObject(Object obj) {
        Class<?> clazz = obj.getClass();

        if (clazz.isAnnotationPresent(Element.class)) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : Arrays.stream(fields)
                    .filter(f -> f.isAnnotationPresent(Injected.class))
                    .toList()) {

                field.setAccessible(true);

                Class<?> fieldClazz = field.getType();

                try {

                    if (config.containsKey(fieldClazz.getSimpleName())) {
                        field.set(obj, config.get(fieldClazz.getName()));
                    } else {

                        Object newInstance = initializeObject(fieldClazz);

                        fieldClazz = newInstance.getClass();

                        config.put(fieldClazz.getName(), newInstance);

                        if (fieldClazz.isAnnotationPresent(Element.class)) configureObject(newInstance);

                        field.set(obj, newInstance);
                    }
                } catch (IllegalAccessException | InvocationTargetException |
                NoSuchMethodException | InstantiationException e) {
                    throw new RuntimeException("Class Injection error" + e.getMessage());
                }
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
