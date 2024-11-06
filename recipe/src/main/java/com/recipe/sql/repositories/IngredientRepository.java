package com.recipe.sql.repositories;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.Ingredient;
import com.recipe.models.enums.Unit;
import com.recipe.repositories.IIngredientRepository;
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
public final class IngredientRepository implements IIngredientRepository {

    @Injected
    @NonNull
    private ConnectionPool connectionPool;

    @Override
    public Ingredient findById(Long id) {
        String query = "SELECT * FROM ingredients WHERE id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToIngredient(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ingredient> findByRecipeId(Long recipeId) {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM ingredients WHERE recipe_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, recipeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ingredients.add(mapToIngredient(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    @Override
    public Boolean update(Ingredient ingredient) {
        String updateQuery = "UPDATE ingredients SET name = ?, amount = ?, unit = ? WHERE id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setString(1, ingredient.getName());
            statement.setDouble(2, ingredient.getAmount());
            statement.setString(3, ingredient.getUnit().toString());
            statement.setLong(4, ingredient.getId());
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
    public Boolean deleteById(Long id) {
        String deleteQuery = "DELETE FROM ingredients WHERE id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        String insertQuery = "INSERT INTO ingredients (recipe_id, name, amount, unit) VALUES (?, ?, ?, ?)";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, ingredient.getRecipeId());
            statement.setString(2, ingredient.getName());
            statement.setDouble(3, ingredient.getAmount());
            statement.setString(4, ingredient.getUnit().toString());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ingredient.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private Ingredient mapToIngredient(ResultSet resultSet) throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(resultSet.getLong("id"));
        ingredient.setRecipeId(resultSet.getLong("recipe_id"));
        ingredient.setName(resultSet.getString("name"));
        ingredient.setAmount(resultSet.getInt("amount"));
        ingredient.setUnit(Unit.valueOf(resultSet.getString("unit")));
        return ingredient;
    }
}
