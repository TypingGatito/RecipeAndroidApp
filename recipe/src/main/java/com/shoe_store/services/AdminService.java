package com.shoe_store.services;

import com.shoe_store.models.User;
import com.shoe_store.repositories.IAdminRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AdminService {

    private final IAdminRepository adminRepository;

    public Boolean setBan(Long id, Boolean active) {
        return adminRepository.setBan(id, active);
    }

    public List<User> getUsers() {
        return adminRepository.getUsers();
    }

}
