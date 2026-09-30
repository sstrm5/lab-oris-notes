package ru.oris.notes.dao;

import ru.oris.notes.db.Database;
import ru.oris.notes.model.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteDao {
    public List<Note> findAllByUser(int userId, String search) throws SQLException {
        boolean hasSearch = search != null && !search.isBlank();
        String sql = hasSearch
                ? "SELECT * FROM notes WHERE user_id = ? AND (LOWER(name) LIKE ? ESCAPE '!' OR LOWER(text) LIKE ? ESCAPE '!') ORDER BY updated_at DESC"
                : "SELECT * FROM notes WHERE user_id = ? ORDER BY updated_at DESC";
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            if (hasSearch) {
                String pattern = "%" + escapeLike(search.toLowerCase()) + "%";
                statement.setString(2, pattern);
                statement.setString(3, pattern);
            }
            try (ResultSet result = statement.executeQuery()) {
                List<Note> notes = new ArrayList<>();
                while (result.next()) notes.add(map(result));
                return notes;
            }
        }
    }

    public Optional<Note> findByIdAndUser(int id, int userId) throws SQLException {
        String sql = "SELECT * FROM notes WHERE id = ? AND user_id = ?";
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? Optional.of(map(result)) : Optional.empty();
            }
        }
    }

    public void create(int userId, String name, String text) throws SQLException {
        String sql = "INSERT INTO notes(user_id, name, text) VALUES (?, ?, ?)";
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setString(2, name);
            statement.setString(3, text);
            statement.executeUpdate();
        }
    }

    public boolean update(int id, int userId, String name, String text) throws SQLException {
        String sql = "UPDATE notes SET name = ?, text = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ? AND user_id = ?";
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, text);
            statement.setInt(3, id);
            statement.setInt(4, userId);
            return statement.executeUpdate() == 1;
        }
    }

    public boolean delete(int id, int userId) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM notes WHERE id = ? AND user_id = ?")) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            return statement.executeUpdate() == 1;
        }
    }

    private Note map(ResultSet result) throws SQLException {
        return new Note(result.getInt("id"), result.getInt("user_id"), result.getString("name"),
                result.getString("text"), result.getTimestamp("created_at").toLocalDateTime(),
                result.getTimestamp("updated_at").toLocalDateTime());
    }

    private String escapeLike(String value) {
        return value.replace("!", "!!").replace("%", "!%").replace("_", "!_");
    }
}
