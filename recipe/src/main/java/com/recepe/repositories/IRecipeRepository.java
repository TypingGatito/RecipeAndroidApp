package com.recepe.repositories;

import com.recepe.models.Recipe;

import java.util.List;

public interface IRecipeRepository {

    Recipe getRecipeById(Long id);

    List<Recipe> findAllRecipes();

    List<Recipe> findRecipesBySectionId(Long sectionId);

    List<Recipe> findRecipesByUserId(Long id);

    List<Recipe> findRecipesByName(String name);

    Boolean addRecipe(Recipe recipe);

    Boolean updateRecipe(Recipe recipe);

    List<Recipe> findFavoriteRecipesOfUser(Long id);

    void addToFavourite(Long recipeId, Long userId);

    void removeFromFavourite(Long recipeId, Long userId);

    void removeRecipe(Long recipeId);

}
