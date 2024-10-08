package com.shoe_store.services;

import com.shoe_store.repositories.ingredient.IIngredientRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IngredientService {

    private final IIngredientRepository ingredientRepository;

}
