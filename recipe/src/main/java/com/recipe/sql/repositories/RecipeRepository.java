package com.recipe.sql.repositories;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.Recipe;
import com.recipe.repositories.IRecipeRepository;
import com.recipe.sql.connection.ConnectionPool;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Element
@RequiredArgsConstructor
@NoArgsConstructor
public final class RecipeRepository implements IRecipeRepository {

    @Injected
    @NonNull
    private ConnectionPool connectionPool;

    @Override
    public Recipe getRecipeById(Long id) {
        String query = "SELECT * FROM recipes WHERE id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToRecipe(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<Recipe> findAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipes";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                recipes.add(mapToRecipe(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return recipes;
    }

    @Override
    public List<Recipe> findRecipesBySectionId(Long sectionId) {
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipes WHERE section_id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, sectionId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                recipes.add(mapToRecipe(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return recipes;
    }

    @Override
    public Boolean addRecipe(Recipe recipe) {
        String insertQuery = "INSERT INTO recipes" +
                " (name, section_id, user_id, created_at, time_to_cook," +
                "calories_on_hund_g," +
                "dose_num," +
                "short_description)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, recipe.getName());
            statement.setLong(2, recipe.getSectionId());
            statement.setLong(3, recipe.getUserId());
            statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(5, recipe.getTimeToCook().toString());
            statement.setInt(6, recipe.getCaloriesOnHundG());
            statement.setInt(7, recipe.getDoseNum());
            statement.setString(8, recipe.getShortDescription());

            Duration timeToCook = recipe.getTimeToCook();

            String timeToCookStr = timeToCook.toString();

            statement.setObject(5, timeToCookStr);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    recipe.setId(generatedKeys.getLong(1));
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public Boolean updateRecipe(Recipe recipe) {
        String updateQuery = "UPDATE recipes SET name = ?, section_id = ?, user_id = ?, time_to_cook = ? WHERE id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, recipe.getName());
            statement.setLong(2, recipe.getSectionId());
            statement.setLong(3, recipe.getUserId());

            statement.setString(4, recipe.getTimeToCook().toString());

            statement.setLong(5, recipe.getId());
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Recipe> findRecipesByUserId(Long userId) {
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipes WHERE user_id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                recipes.add(mapToRecipe(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return recipes;
    }

    @Override
    public List<Recipe> findRecipesByName(String name) {
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipes WHERE name = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                recipes.add(mapToRecipe(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return recipes;
    }

    @Override
    public List<Recipe> findFavoriteRecipesOfUser(Long userId) {
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT r.* FROM recipes r " +
                "JOIN favourite_recipes fr ON r.id = fr.recipe_id " +
                "WHERE fr.user_id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                recipes.add(mapToRecipe(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return recipes;
    }

    @Override
    public void addToFavourite(Long recipeId, Long userId) {
        String insertQuery = "INSERT INTO favourite_recipes (user_id, recipe_id) VALUES (?, ?)";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setLong(1, userId);
            statement.setLong(2, recipeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void removeFromFavourite(Long recipeId, Long userId) {
        String deleteQuery = "DELETE FROM favourite_recipes WHERE user_id = ? AND recipe_id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, userId);
            statement.setLong(2, recipeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void removeRecipe(Long recipeId) {
        String deleteQuery = "DELETE FROM recipes WHERE id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, recipeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private Recipe mapToRecipe(ResultSet resultSet) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setId(resultSet.getLong("id"));
        recipe.setName(resultSet.getString("name"));
        recipe.setSectionId(resultSet.getLong("section_id"));
        recipe.setUserId(resultSet.getLong("user_id"));
        recipe.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        recipe.setTimeToCook(Duration.parse(resultSet.getString("time_to_cook")));
        recipe.setCaloriesOnHundG(resultSet.getInt("calories_on_hund_g"));
        recipe.setDoseNum(resultSet.getInt("dose_num"));
        recipe.setShortDescription(resultSet.getString("short_description"));

        return recipe;
    }

}
