package com.shoe_store.services;

import com.shoe_store.repositories.recipe.IRecipeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecipeService {

    private final IRecipeRepository recipeRepository;

}
