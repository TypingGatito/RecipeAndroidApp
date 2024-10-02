package com.shoe_store.repositories.recipe;

import com.shoe_store.models.Recipe;
import com.shoe_store.models.User;
import com.shoe_store.repositories.user.UserRepository;
import java.util.ArrayList;
import java.util.List;

public final class RecipeRepository implements IRecipeRepository {

    private static RecipeRepository instance;

    public static synchronized RecipeRepository getInstance() {
        if (instance == null) instance = new RecipeRepository();

        return instance;
    }

    private RecipeRepository() {
    }

    private final List<User> users = new ArrayList<>();

    @Override
    public Recipe getRecipeById(Long id) {
        return null;
    }

    @Override
    public List<Recipe> findAllRecipes() {
        return List.of();
    }

    @Override
    public List<Recipe> findRecipesBySectionId(Long id) {
        return List.of();
    }

    @Override
    public List<Recipe> getRecipesByName(String name) {
        return List.of();
    }

    @Override
    public Boolean addRecipe(Recipe recipe) {
        return null;
    }

    @Override
    public Boolean updateRecipe(Recipe recipe) {
        return null;
    }

}
