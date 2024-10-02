package com.shoe_store.repositories.ingredient;

import com.shoe_store.models.Ingredient;
import com.shoe_store.models.User;
import com.shoe_store.repositories.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public final class IngredientRepository implements IIngredientRepository {

    private static IngredientRepository instance;

    public static synchronized IngredientRepository getInstance() {
        if (instance == null) instance = new IngredientRepository();

        return instance;
    }

    private IngredientRepository() {
    }

    private final List<User> users = new ArrayList<>();

    @Override
    public Ingredient findById(Long id) {
        return null;
    }

    @Override
    public List<Ingredient> findByRecipeId(Long id) {
        return List.of();
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
