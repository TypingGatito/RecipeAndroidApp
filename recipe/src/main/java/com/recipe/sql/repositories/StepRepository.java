package com.recipe.sql.repositories;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.Step;
import com.recipe.repositories.IStepRepository;
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
public final class StepRepository implements IStepRepository {

    @Injected
    @NonNull
    private ConnectionPool connectionPool;

    @Override
    public Step findStepById(Long id) {
        String query = "SELECT * FROM steps WHERE id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToStep(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<Step> findRecipeSteps(Long recipeId) {
        List<Step> steps = new ArrayList<>();
        String query = "SELECT * FROM steps WHERE recipe_id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, recipeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                steps.add(mapToStep(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return steps;
    }

    @Override
    public Boolean addStep(Step step) {
        String insertQuery = "INSERT INTO steps (recipe_id, text, num) VALUES (?, ?, ?)";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, step.getRecipeId());
            statement.setString(2, step.getText());
            statement.setInt(3, step.getNum());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    step.setId(generatedKeys.getLong(1));
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
    public Boolean updateStep(Step step) {
        String updateQuery = "UPDATE steps SET text = ?, num = ? WHERE id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, step.getText());
            statement.setInt(2, step.getNum());
            statement.setLong(3, step.getId());
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
    public Boolean deleteStep(Long stepId) {
        String deleteQuery = "DELETE FROM steps WHERE id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, stepId);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private Step mapToStep(ResultSet resultSet) throws SQLException {
        Step step = new Step();
        step.setId(resultSet.getLong("id"));
        step.setRecipeId(resultSet.getLong("recipe_id"));
        step.setText(resultSet.getString("text"));
        step.setNum(resultSet.getInt("num"));
        return step;
    }
}
