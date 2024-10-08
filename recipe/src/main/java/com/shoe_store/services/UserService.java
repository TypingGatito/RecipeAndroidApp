package com.shoe_store.services;

import com.shoe_store.models.Recipe;
import com.shoe_store.models.User;
import com.shoe_store.repositories.recipe.IRecipeRepository;
import com.shoe_store.repositories.user.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;

    private final IRecipeRepository recipeRepository;

    public Boolean login(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) return false;

        return user.getPassword().equals(password);
    }

    public Boolean addUser(User user) {

        return userRepository.addUser(user);
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Boolean updateUser(User user) {

        return userRepository.updateUser(user);
    }

    public Boolean deleteUser(Long id) {
        return userRepository.deleteUser(id);
    }

    public List<Recipe> findUserCreatedRecipe(Long userId) {
        return recipeRepository.findRecipesByUserId(userId);
    }

    public List<Recipe> findUserFavouriteRecipe(Long userId) {
        return recipeRepository.findRecipesByUserId(userId);
    }

}
