package com.recipe.sql_repositories;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.in_memory.db.InMemoryInfo;
import com.recipe.models.Ingredient;
import com.recipe.repositories.IIngredientRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Element
@RequiredArgsConstructor
@NoArgsConstructor
public final class IngredientRepository implements IIngredientRepository {

    @Injected
    @NonNull
    private InMemoryInfo inMemoryInfo;

    @Override
    public Ingredient findById(Long id) {
        return inMemoryInfo
                .getIngredients()
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Ingredient> findByRecipeId(Long recipeId) {
        return inMemoryInfo
                .getIngredients()
                .stream()
                .filter(i -> i.getRecipeId().equals(recipeId))
                .toList();
    }

    @Override
    public Boolean update(Ingredient ingredient) {
        return null;
    }

    @Override
    public Boolean deleteById(Long id) {
        return null;
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        ingredient.setId(inMemoryInfo.getIngredients()
                .stream()
                .mapToLong(Ingredient::getId)
                .max()
                .orElse(0) + 1);
        inMemoryInfo.getIngredients().add(ingredient);
    }

}
