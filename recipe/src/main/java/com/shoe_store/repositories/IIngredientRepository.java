package com.shoe_store.repositories;

import com.shoe_store.models.Ingredient;

import java.util.List;

public interface IIngredientRepository {

    Ingredient findById(Long id);

    List<Ingredient> findByRecipeId(Long id);

    Boolean update(Ingredient ingredient);

    Boolean deleteById(Long id);

}
