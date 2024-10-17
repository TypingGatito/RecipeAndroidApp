package com.shoe_store.in_memory.repositories;

import com.shoe_store.in_memory.db.InMemoryInfo;
import com.shoe_store.models.User;
import com.shoe_store.repositories.IAdminRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class AdminRepository implements IAdminRepository {
    private final InMemoryInfo inMemoryInfo;

    @Override
    public Boolean setBan(Long id, Boolean active) {
        User user = inMemoryInfo
                .getUsers()
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (user == null) {return false;}

        user.setIsActive(active);

        return true;
    }

    @Override
    public List<User> getUsers() {
        return inMemoryInfo.getUsers();
    }

}
