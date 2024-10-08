package com.shoe_store.repositories.ingredient;

import com.shoe_store.in_memory_info.InMemoryInfo;
import com.shoe_store.models.Ingredient;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class IngredientRepository implements IIngredientRepository {

    private final InMemoryInfo inMemoryInfo;

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

}