package com.recipe.sql.repositories;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.in_memory.db.InMemoryInfo;
import com.recipe.models.Section;
import com.recipe.repositories.ISectionRepository;
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
public final class SectionRepository implements ISectionRepository {

    @Injected
    @NonNull
    private ConnectionPool connectionPool;

    @Override
    public List<Section> findAllSections() {
        String query = "SELECT * FROM sections";

        List<Section> sections = new ArrayList<>();

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sections.add(mapToSection(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return sections;
    }

    private Section mapToSection(ResultSet resultSet) throws SQLException {
        Section section = new Section();
        section.setId(resultSet.getLong("id"));
        section.setName(resultSet.getString("name"));

        return section;
    }
}
