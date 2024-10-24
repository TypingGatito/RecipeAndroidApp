package com.recipe.repositories;

import com.recipe.models.User;

import java.util.List;

public interface IAdminRepository {

    Boolean setBan(Long id, Boolean active);

    List<User> getUsers();

}
