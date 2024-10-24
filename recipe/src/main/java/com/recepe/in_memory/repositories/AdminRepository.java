package com.recepe.in_memory.repositories;

import com.recepe.annotations.Element;
import com.recepe.annotations.Injected;
import com.recepe.in_memory.db.InMemoryInfo;
import com.recepe.models.User;
import com.recepe.repositories.IAdminRepository;
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
