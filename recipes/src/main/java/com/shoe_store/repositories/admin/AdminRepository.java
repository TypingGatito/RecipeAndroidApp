package com.shoe_store.repositories.admin;

import com.shoe_store.models.User;
import com.shoe_store.repositories.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public final class AdminRepository implements IAdminRepository {

    private static AdminRepository instance;

    public static synchronized AdminRepository getInstance() {
        if (instance == null) instance = new AdminRepository();

        return instance;
    }

    private AdminRepository() {
    }

    private final List<User> users = new ArrayList<>();


    @Override
    public void setBan(Long id, Boolean active) {

    }

    @Override
    public List<User> getUsers() {
        return List.of();
    }

}
