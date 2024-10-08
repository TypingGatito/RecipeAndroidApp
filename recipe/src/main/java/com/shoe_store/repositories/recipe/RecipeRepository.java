package com.shoe_store.repositories.recipe;

import com.shoe_store.in_memory_info.InMemoryInfo;
import com.shoe_store.models.Recipe;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public final class RecipeRepository implements IRecipeRepository {

    private final InMemoryInfo inMemoryInfo;

    @Override
    public Recipe getRecipeById(Long id) {
        return inMemoryInfo
                .getRecipes()
                .stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Recipe> findAllRecipes() {
        return inMemoryInfo
                .getRecipes();
    }

    @Override
    public List<Recipe> findRecipesBySectionId(Long sectionId) {
        return inMemoryInfo
                .getRecipes()
                .stream()
                .filter(r -> r.getSectionId().equals(sectionId))
                .toList();
    }

    @Override
    public Boolean addRecipe(Recipe recipe) {
        return inMemoryInfo.getRecipes().add(recipe);
    }

    @Override
    public Boolean updateRecipe(Recipe recipe) {
        return null;
    }

    @Override
    public List<Recipe> findRecipesByUserId(Long id) {
        List<Recipe> recipes = inMemoryInfo.getRecipes();

        return recipes
                .stream()
                .filter((u) -> u.getUserId().equals(id))
                .toList();
    }

    @Override
    public List<Recipe> findRecipesByName(String name) {
        return inMemoryInfo
                .getRecipes()
                .stream()
                .filter(r -> r.getName().equals(name))
                .toList();
    }

    @Override
    public List<Recipe> findFavoriteRecipesOfUser(Long id) {
        List<Recipe> recipes = inMemoryInfo.getRecipes();

        Set<Long> favourites = inMemoryInfo.getUserFavouriteRecipe().getRecipesByUser(id);

        return recipes
                .stream()
                .filter((u) -> favourites.contains(u.getId()))
                .toList();
    }

    @Override
    public Double findRatingByRecipeId(Long recipeId) {
        List<Double> favourites = inMemoryInfo.getRatings().
                stream()
                .filter(r -> r.getRecipeId().equals(recipeId))
                .map(r -> r.getRating())
                .toList();

        Double sum = 0d;
        for (Double el: favourites) sum += el;
        return sum / favourites.size();
    }

}
