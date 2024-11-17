package com.recipe.sql.repositories;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.User;
import com.recipe.models.enums.UserRole;
import com.recipe.repositories.IUserRepository;
import com.recipe.sql.connection.ConnectionPool;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@Element
@RequiredArgsConstructor
@NoArgsConstructor
public final class UserRepository implements IUserRepository {

    @Injected
    @NonNull
    private ConnectionPool connectionPool;

    @Override
    public Set<UserRole> getUserRoles(Long userId) {
        Set<UserRole> roles = new HashSet<>();
        String query = "SELECT role FROM user_role WHERE user_id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                roles.add(UserRole.valueOf(resultSet.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return roles;
    }

    @Override
    public Boolean addUser(User user) {
        if (user == null) return false;

        user.setIsActive(true);

        String insertUserQuery = "INSERT INTO users (email, password, login, is_active) VALUES (?, ?, ?, ?)";
        String insertRoleQuery = "INSERT INTO user_role (user_id, role) VALUES (?, ?)";
        String getUserID = "SELECT id FROM users WHERE email = ?";
        Connection connection = connectionPool.getConnection();
        try {
            if (findUserByEmail(user.getEmail()) != null) {
                return false;
            }

            try (PreparedStatement userStatement = connection.prepareStatement(insertUserQuery)) {
                //userStatement.setLong(1, user.getId());
                userStatement.setString(1, user.getEmail());
                userStatement.setString(2, user.getPassword());
                userStatement.setString(3, user.getLogin());
                userStatement.setBoolean(4, user.getIsActive());
                userStatement.executeUpdate();
            }

            Long userId = 0L;
            try (PreparedStatement userStatement = connection.prepareStatement(getUserID)) {
                userStatement.setString(1, user.getEmail());
                ResultSet resultSet = userStatement.executeQuery();
                if (resultSet.next()) userId = resultSet.getLong("id");
            }

            try (PreparedStatement roleStatement = connection.prepareStatement(insertRoleQuery)) {
                roleStatement.setLong(1, userId);
                roleStatement.setString(2, UserRole.USER.name());
                roleStatement.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public User findUserById(Long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        User user = null;

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = mapToUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        User user = null;

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = mapToUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return user;
    }

    @Override
    public Boolean updateUser(User user) {
        if (user == null) return false;

        String query = "UPDATE users SET email = ?, password = ?, login = ?, is_active = ? WHERE id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getLogin());
            statement.setBoolean(4, user.getIsActive());
            statement.setLong(5, user.getId());
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Boolean deleteUser(Long id) {
        String deleteRolesQuery = "DELETE FROM user_roles WHERE user_id = ?";
        String deleteUserQuery = "DELETE FROM users WHERE id = ?";

        Connection connection = connectionPool.getConnection();
        try {
            try (PreparedStatement rolesStatement = connection.prepareStatement(deleteRolesQuery)) {
                rolesStatement.setLong(1, id);
                rolesStatement.executeUpdate();
            }

            try (PreparedStatement userStatement = connection.prepareStatement(deleteUserQuery)) {
                userStatement.setLong(1, id);
                userStatement.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private User mapToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setLogin(resultSet.getString("login"));
        user.setIsActive(resultSet.getBoolean("is_active"));
        return user;
    }

}
