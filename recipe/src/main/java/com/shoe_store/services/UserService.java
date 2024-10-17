package com.shoe_store.services;

import com.shoe_store.models.Recipe;
import com.shoe_store.models.User;
import com.shoe_store.models.enums.UserRole;
import com.shoe_store.repositories.IRecipeRepository;
import com.shoe_store.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;

    private final IRecipeRepository recipeRepository;

    public Set<UserRole> getUserRoles(Long userId) {
        return userRepository.getUserRoles(userId);
    }

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
