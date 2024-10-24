package com.recipe.services;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.User;
import com.recipe.repositories.IAdminRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Element
@NoArgsConstructor
@RequiredArgsConstructor
public class AdminService {

    @Injected
    @NonNull
    private IAdminRepository adminRepository;

    public Boolean setBan(Long id, Boolean active) {
        return adminRepository.setBan(id, active);
    }

    public List<User> getUsers() {
        return adminRepository.getUsers();
    }

}
