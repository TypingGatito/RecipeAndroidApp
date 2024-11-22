package com.recipe.sql.repositories;


import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.in_memory.db.InMemoryInfo;
import com.recipe.models.Ingredient;
import com.recipe.models.Rating;
import com.recipe.models.enums.Unit;
import com.recipe.repositories.IRatingRepository;
import com.recipe.sql.connection.ConnectionPool;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Element
@RequiredArgsConstructor
@NoArgsConstructor
public final class RatingRepository implements IRatingRepository {

    @Injected
    @NonNull
    private ConnectionPool connectionPool;

    @Override
    public List<Rating> findRatingsByUserId(Long id) {
        return List.of();
    }

    @Override
    public List<Rating> findRatingsByRecipeId(Long recipeId) {
        String query = "SELECT * FROM rating WHERE recipe_id = ?";

        List<Rating> ratings = new ArrayList<>();

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, recipeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ratings.add(mapToRating(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Rating findRating(Long userId, Long recipeId) {
        String query = "SELECT * FROM rating WHERE recipe_id = ? AND user_id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, recipeId);
            statement.setLong(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToRating(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void setRating(Long userId, Long recipeId, Double ratingV) {
        String checkQuery = "SELECT COUNT(*) FROM rating WHERE recipe_id = ? AND user_id = ?";
        String updateQuery = "UPDATE rating SET rating = ? WHERE recipe_id = ? AND user_id = ?";
        String insertQuery = "INSERT INTO rating (recipe_id, user_id, rating) VALUES (?, ?, ?)";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            checkStatement.setLong(1, recipeId);
            checkStatement.setLong(2, userId);
            ResultSet resultSet = checkStatement.executeQuery();
            boolean exists = false;
            if (resultSet.next()) {
                exists = resultSet.getInt(1) > 0;
            }

            if (exists) {

                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setDouble(1, ratingV);
                    updateStatement.setLong(2, recipeId);
                    updateStatement.setLong(3, userId);
                    updateStatement.executeUpdate();
                }
            } else {

                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setLong(1, recipeId);
                    insertStatement.setLong(2, userId);
                    insertStatement.setDouble(3, ratingV);
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public Double findRatingByRecipeId(Long recipeId) {
        String query = "SELECT AVG(rating) FROM rating WHERE recipe_id = ?";

        Connection connection = connectionPool.getConnection();
        try (
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, recipeId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return null;
    }

    @Override
    public void deleteRating(Long userId, Long recipeId) {
        String query = "DELETE FROM rating WHERE recipe_id = ? AND user_id = ?";

        Connection connection = connectionPool.getConnection();
        try (
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, recipeId);
            statement.setLong(2, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private Rating mapToRating(ResultSet resultSet) throws SQLException {
        Rating rating = new Rating();
        rating.setRecipeId(resultSet.getLong("recipe_id"));
        rating.setUserId(resultSet.getLong("user_id"));
        rating.setRating(resultSet.getDouble("rating"));

        return rating;
    }
}
