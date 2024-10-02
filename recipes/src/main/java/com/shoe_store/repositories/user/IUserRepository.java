package com.shoe_store.repositories.user;

import com.shoe_store.models.User;

public interface IUserRepository {

    User findUserById(Long id);

    User findUserByEmail(String email);

    Boolean addUser(User user);

    Boolean login(String email, String password);

    Boolean updateUser(User user);

    Boolean deleteUser(Long id);

}
