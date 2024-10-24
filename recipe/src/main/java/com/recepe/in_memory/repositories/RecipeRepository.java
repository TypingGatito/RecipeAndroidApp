package com.recepe.in_memory.repositories;

import com.recepe.annotations.Element;
import com.recepe.annotations.Injected;
import com.recepe.in_memory.db.InMemoryInfo;
import com.recepe.models.Recipe;
import com.recepe.repositories.IRecipeRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Element
@RequiredArgsConstructor
@NoArgsConstructor
public final class RecipeRepository implements IRecipeRepository {

    @Injected
    @NonNull
    private InMemoryInfo inMemoryInfo;

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
        Long m = inMemoryInfo
                .getRecipes()
                .stream()
                .mapToLong(Recipe::getId)
                .max()
                .orElse(0) + 1;
        recipe.setId(m);
        recipe.setCreatedAt(LocalDateTime.now());
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
    public void addToFavourite(Long recipeId, Long userId) {
        inMemoryInfo.getUserFavouriteRecipe().addUserRecipe(userId, recipeId);
    }

    @Override
    public void removeFromFavourite(Long recipeId, Long userId) {
        inMemoryInfo.getUserFavouriteRecipe().removeUserRecipe(userId, recipeId);
    }

    @Override
    public void removeRecipe(Long recipeId) {
        inMemoryInfo.getRecipes().removeIf(r -> r.getId().equals(recipeId));
    }

}
