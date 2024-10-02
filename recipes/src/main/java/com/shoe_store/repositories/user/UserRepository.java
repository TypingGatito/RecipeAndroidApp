package com.shoe_store.repositories.user;

import com.shoe_store.models.User;

import java.util.ArrayList;
import java.util.List;

public final class UserRepository implements IUserRepository {

    private static UserRepository instance;

    public static synchronized UserRepository getInstance() {
        if (instance == null) instance = new UserRepository();

        return instance;
    }

    private UserRepository() {
    }

    private final List<User> users = new ArrayList<>();

    @Override
    public Boolean addUser(User user) {
        if (user == null) return false;
        for (User u : users) {
            if (u.getEmail().equals(user.getEmail())) {
                return false;
            }
        }

        return users.add(user);
    }

    @Override
    public User findUserById(Long id) {
        return users
                .stream()
                .filter((user) -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return users
                .stream()
                .filter((user) -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Boolean login(String email, String password) {
        User user = users
                .stream()
                .filter((u) -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        if (user == null) return false;

        return user.getPassword().equals(password);
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
        return users.removeIf((user) -> user.getId().equals(id));
    }

}
