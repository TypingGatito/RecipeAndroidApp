package com.recepe.in_memory.repositories;

import com.recepe.annotations.Element;
import com.recepe.annotations.Injected;
import com.recepe.in_memory.db.InMemoryInfo;
import com.recepe.models.User;
import com.recepe.models.enums.UserRole;
import com.recepe.repositories.IUserRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Element
@RequiredArgsConstructor
@NoArgsConstructor
public final class UserRepository implements IUserRepository {

    @Injected
    @NonNull
    private InMemoryInfo inMemoryInfo;

    @Override
    public Set<UserRole> getUserRoles(Long userId) {
        return inMemoryInfo.getUserRoles().get(userId);
    }

    @Override
    public Boolean addUser(User user) {
        if (user == null) return false;
        for (User u : inMemoryInfo.getUsers()) {
            if (u.getEmail().equals(user.getEmail())) {
                return false;
            }
        }

        Set<UserRole> rolesSet = new HashSet<>();
        rolesSet.add(UserRole.USER);
        inMemoryInfo.getUserRoles().put(user.getId(), rolesSet);

        user.setIsActive(true);

        return inMemoryInfo.getUsers().add(user);
    }

    @Override
    public User findUserById(Long id) {
        return inMemoryInfo.getUsers()
                .stream()
                .filter((user) -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return inMemoryInfo.getUsers()
                .stream()
                .filter((user) -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Boolean updateUser(User user) {
        if (user == null) return false;

        User oldUser = findUserById(user.getId());

        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setLogin(user.getLogin());
        oldUser.setIsActive(user.getIsActive());

        return true;
    }

    @Override
    public Boolean deleteUser(Long id) {
        return inMemoryInfo.getUsers().removeIf((user) -> user.getId().equals(id));
    }

    @Override
    public Set<UserRole> findUserRoleById(Long id) {
        return inMemoryInfo
                .getUserRoles()
                .get(id);
    }

}
