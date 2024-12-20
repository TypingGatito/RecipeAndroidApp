package com.recipe.sql.repositories;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.Commentary;
import com.recipe.repositories.ICommentaryRepository;
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
public final class CommentaryRepository implements ICommentaryRepository {

    @Injected
    @NonNull
    private ConnectionPool connectionPool;

    @Override
    public List<Commentary> findUserCommentaries(Long userId) {
        List<Commentary> commentaries = new ArrayList<>();
        String query = "SELECT * FROM commentaries WHERE user_id = ?";

        Connection connection = connectionPool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                commentaries.add(mapToCommentary(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return commentaries;
    }

    @Override
    public List<Commentary> findStepCommentaries(Long stepId) {
        List<Commentary> commentaries = new ArrayList<>();
        String query = "SELECT * FROM commentaries WHERE step_id = ?";

        Connection connection = connectionPool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, stepId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                commentaries.add(mapToCommentary(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return commentaries;
    }

    @Override
    public List<Commentary> findUserStepCommentaries(Long userId, Long stepId) {
        List<Commentary> commentaries = new ArrayList<>();
        String query = "SELECT * FROM commentaries WHERE user_id = ? AND step_id = ?";

        Connection connection = connectionPool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, stepId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                commentaries.add(mapToCommentary(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return commentaries;
    }

    @Override
    public Boolean addCommentary(Commentary commentary) {
        commentary.setOrderNum(getNextOrderNum(commentary.getUserId(), commentary.getStepId()) + 1);

        String insertQuery = "INSERT INTO commentaries (user_id, step_id, text, order_num) VALUES (?, ?, ?, ?)";
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setLong(1, commentary.getUserId());
            statement.setLong(2, commentary.getStepId());
            statement.setString(3, commentary.getText());
            statement.setInt(4, commentary.getOrderNum());
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
    public Boolean updateCommentary(Commentary commentary) {
        String updateQuery = "UPDATE commentaries SET text = ? WHERE user_id = ? AND step_id = ? AND order_num = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, commentary.getText());
            statement.setLong(2, commentary.getUserId());
            statement.setLong(3, commentary.getStepId());
            statement.setInt(4, commentary.getOrderNum());
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
    public Boolean deleteCommentary(Long userId, Long stepId, Integer orderNum) {
        String deleteQuery = "DELETE FROM commentaries WHERE user_id = ? AND step_id = ? AND order_num = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, userId);
            statement.setLong(2, stepId);
            statement.setInt(3, orderNum);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private int getNextOrderNum(Long userId, Long stepId) {
        String query = "SELECT COALESCE(MAX(order_num), 0) AS max_order_num FROM commentaries WHERE user_id = ? AND step_id = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, stepId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("max_order_num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return 0;
    }

    private Commentary mapToCommentary(ResultSet resultSet) throws SQLException {
        Commentary commentary = new Commentary();
        commentary.setUserId(resultSet.getLong("user_id"));
        commentary.setStepId(resultSet.getLong("step_id"));
        commentary.setText(resultSet.getString("text"));
        commentary.setOrderNum(resultSet.getInt("order_num"));
        return commentary;
    }
}
