package com.recepe.services;

import com.recepe.models.User;
import com.recepe.repositories.IAdminRepository;
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
