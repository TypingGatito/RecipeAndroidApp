package com.recepe.repositories;

import com.recepe.models.Ingredient;

import java.util.List;

public interface IIngredientRepository {

    Ingredient findById(Long id);

    List<Ingredient> findByRecipeId(Long id);

    Boolean update(Ingredient ingredient);

    Boolean deleteById(Long id);

    void addIngredient(Ingredient ingredient);

}
