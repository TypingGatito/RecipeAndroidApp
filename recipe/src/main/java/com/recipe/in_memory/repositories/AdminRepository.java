package com.recipe.in_memory.repositories;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.in_memory.db.InMemoryInfo;
import com.recipe.models.User;
import com.recipe.repositories.IAdminRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Element
@RequiredArgsConstructor
@NoArgsConstructor
public final class AdminRepository implements IAdminRepository {

    @Injected
    @NonNull
    private InMemoryInfo inMemoryInfo;

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
