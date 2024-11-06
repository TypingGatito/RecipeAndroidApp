package com.recipe.repositories;

import com.recipe.models.User;
import com.recipe.models.enums.UserRole;

import java.util.Set;

public interface IUserRepository {

    User findUserById(Long id);

    User findUserByEmail(String email);

    Boolean addUser(User user);

    Boolean updateUser(User user);

    Boolean deleteUser(Long id);

    Set<UserRole> getUserRoles(Long userId);

}
