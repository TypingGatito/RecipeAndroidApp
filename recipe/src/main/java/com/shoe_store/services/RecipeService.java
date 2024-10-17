package com.shoe_store.services;

import com.shoe_store.models.Recipe;
import com.shoe_store.repositories.IRecipeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RecipeService {

    private final IRecipeRepository recipeRepository;

    public Recipe getRecipeById(Long id) {
        return recipeRepository.getRecipeById(id);
    }

    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAllRecipes();
    }

    public List<Recipe> findFavoriteRecipesOfUser(Long id) {
        return recipeRepository.findFavoriteRecipesOfUser(id);
    }

    public List<Recipe> findRecipesBySectionId(Long sectionId) {
        return recipeRepository.findRecipesBySectionId(sectionId);
    }

    public void addToFavourite(Long recipeId, Long userId) {
        recipeRepository.addToFavourite(recipeId, userId);
    }

    public void removeFromFavourite(Long recipeId, Long userId) {
        recipeRepository.removeFromFavourite(recipeId, userId);
    }

    public Boolean isInFavourite(Long recipeId, Long userId) {
        return recipeRepository.findFavoriteRecipesOfUser(userId)
                .stream()
                .anyMatch(recipe -> recipeId.equals(recipe.getId()));
    }

}
