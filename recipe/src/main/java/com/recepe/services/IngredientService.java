package com.recepe.services;

import com.recepe.models.Ingredient;
import com.recepe.repositories.IIngredientRepository;
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
