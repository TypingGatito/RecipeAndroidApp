package com.recepe.repositories;

import com.recepe.models.User;

import java.util.List;

public interface IAdminRepository {

    Boolean setBan(Long id, Boolean active);

    List<User> getUsers();

}
