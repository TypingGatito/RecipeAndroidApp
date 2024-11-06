package com.recipe.sql.repositories;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.User;
import com.recipe.repositories.IAdminRepository;
import com.recipe.sql.connection.ConnectionPool;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Element
@RequiredArgsConstructor
@NoArgsConstructor
public final class AdminRepository implements IAdminRepository {

    @Injected
    @NonNull
    private ConnectionPool connectionPool;

    @Override
    public Boolean setBan(Long id, Boolean active) {
        String query = "UPDATE users SET is_active = ? WHERE id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, active);
            statement.setLong(2, id);
            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = mapToUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return users;
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
