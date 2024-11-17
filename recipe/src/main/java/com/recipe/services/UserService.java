package com.recipe.services;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.Recipe;
import com.recipe.models.User;
import com.recipe.models.enums.UserRole;
import com.recipe.repositories.IRecipeRepository;
import com.recipe.repositories.IUserRepository;
import com.recipe.utils.PasswordHasher;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Element
@NoArgsConstructor
@RequiredArgsConstructor
public class UserService {

    @Injected
    @NonNull
    private IUserRepository userRepository;

    @Injected
    @NonNull
    private IRecipeRepository recipeRepository;

    public Set<UserRole> getUserRoles(Long userId) {
        return userRepository.getUserRoles(userId);
    }

    public Boolean login(String email, String password) {
        User user = userRepository.findUserByEmail(email);

        if (user == null) return false;

        return PasswordHasher.verifyPassword(password, user.getPassword());
    }

    public Boolean addUser(User user) {
        user.setPassword(PasswordHasher.hashPassword(user.getPassword()));

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
