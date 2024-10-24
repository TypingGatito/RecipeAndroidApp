package com.recipe.services;

import com.recipe.annotations.Element;
import com.recipe.models.User;
import com.recipe.repositories.IAdminRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Element
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
