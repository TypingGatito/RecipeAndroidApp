package com.recepe.in_memory.in_memory_m_t_m_tables;

import com.recepe.annotations.Element;

import java.util.*;

@Element
public final class UserFavouriteRecipe {

    private static UserFavouriteRecipe instance;

    public static synchronized UserFavouriteRecipe getInstance() {
        if (instance == null) instance = new UserFavouriteRecipe();

        return instance;
    }

    private UserFavouriteRecipe() {
    }

    private final Map<Long, Set<Long>> userToRecipes = new HashMap<>();

    private final Map<Long, Set<Long>> recipeToUser = new HashMap<>();

    public void userCreatesRecipe(Long userId, Long recipeId) {
        userToRecipes
                .computeIfAbsent(userId, k -> new HashSet<>())
                .add(recipeId);

        recipeToUser
                .computeIfAbsent(recipeId, k -> new HashSet<>())
                .add(userId);
    }

    public Set<Long> getRecipesByUser(Long userId) {
        return userToRecipes.getOrDefault(userId, Collections.emptySet());
    }

    public Set<Long> getUsersByRecipe(Long recipeId) {
        return recipeToUser.getOrDefault(recipeId, Collections.emptySet());
    }

    public void removeUserRecipe(Long userId, Long recipeId) {
        Set<Long> recipes = userToRecipes.get(userId);
        if (recipes != null) {
            recipes.remove(recipeId);
            if (recipes.isEmpty()) {
                userToRecipes.remove(userId);
            }
        }

        Set<Long> users = recipeToUser.get(recipeId);
        if (users != null) {
            users.remove(userId);
            if (users.isEmpty()) {
                recipeToUser.remove(recipeId);
            }
        }
    }

    public void addUserRecipe(Long userId, Long recipeId) {
        if (!userToRecipes.containsKey(userId)) {
            userToRecipes.put(userId, new HashSet<>());
        }

        userToRecipes.get(userId).add(recipeId);
    }

}
