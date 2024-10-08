package com.shoe_store.repositories.recipe;

import com.shoe_store.models.Recipe;

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

    Double findRatingByRecipeId(Long recipeId);

}
