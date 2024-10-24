package com.recipe.services;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.Ingredient;
import com.recipe.repositories.IIngredientRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Element
@NoArgsConstructor
@RequiredArgsConstructor
public class IngredientService {

    @Injected
    @NonNull
    private IIngredientRepository ingredientRepository;

    public List<Ingredient> findByRecipeId(Long recipeId) {
        return ingredientRepository.findByRecipeId(recipeId);
    }

    public void add(Ingredient ingredient) {
        ingredientRepository.addIngredient(ingredient);
    }
}
