package com.recipe.services;

import com.recipe.annotations.Element;
import com.recipe.models.Recipe;
import com.recipe.repositories.IRecipeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Element
@RequiredArgsConstructor
public class RecipeService {

    private final IRecipeRepository recipeRepository;

    public Recipe findRecipeById(Long id) {
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

    public List<Recipe> findRecipesByUserId(Long id) {
        return recipeRepository.findRecipesByUserId(id);
    }

    public void removeRecipe(Long recipeId) {
        recipeRepository.removeRecipe(recipeId);
    }

    public Boolean addRecipe(Recipe recipe) {
        return recipeRepository.addRecipe(recipe);
    }

}
