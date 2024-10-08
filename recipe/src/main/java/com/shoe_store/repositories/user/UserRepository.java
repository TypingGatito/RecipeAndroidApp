package com.shoe_store.repositories.user;

import com.shoe_store.in_memory_info.InMemoryInfo;
import com.shoe_store.models.User;
import com.shoe_store.models.enums.UserRole;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class UserRepository implements IUserRepository {

    private final InMemoryInfo inMemoryInfo;

    @Override
    public Boolean addUser(User user) {
        if (user == null) return false;
        for (User u : inMemoryInfo.getUsers()) {
            if (u.getEmail().equals(user.getEmail())) {
                return false;
            }
        }

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
