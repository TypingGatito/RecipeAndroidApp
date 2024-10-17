package com.shoe_store.services;

import com.shoe_store.models.Ingredient;
import com.shoe_store.repositories.IIngredientRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IngredientService {

    private final IIngredientRepository ingredientRepository;

    public List<Ingredient> findByRecipeId(Long recipeId) {
        return ingredientRepository.findByRecipeId(recipeId);
    }

}
