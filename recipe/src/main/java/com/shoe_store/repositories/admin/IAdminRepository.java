package com.shoe_store.repositories.admin;

import com.shoe_store.models.User;

import java.util.List;

public interface IAdminRepository {

    Boolean setBan(Long id, Boolean active);

    List<User> getUsers();

}
