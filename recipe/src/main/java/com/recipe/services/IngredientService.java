package com.recipe.services;

import com.recipe.models.Ingredient;
import com.recipe.repositories.IIngredientRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IngredientService {

    private final IIngredientRepository ingredientRepository;

    public List<Ingredient> findByRecipeId(Long recipeId) {
        return ingredientRepository.findByRecipeId(recipeId);
    }

    public void add(Ingredient ingredient) {
        ingredientRepository.addIngredient(ingredient);
    }
}
