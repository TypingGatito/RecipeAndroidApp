package com.shoe_store.repositories.user;

import com.shoe_store.models.User;
import com.shoe_store.models.enums.UserRole;

import java.util.Set;

public interface IUserRepository {

    User findUserById(Long id);

    User findUserByEmail(String email);

    Boolean addUser(User user);

    Boolean updateUser(User user);

    Boolean deleteUser(Long id);

    Set<UserRole> findUserRoleById(Long id);
}
