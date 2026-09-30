package ru.oris.notes.dao;

import ru.oris.notes.db.Database;
import ru.oris.notes.model.User;

import java.sql.*;
import java.util.Optional;

public class UserDao {
    public User create(String login, String passwordHash) throws SQLException {
        String sql = "INSERT INTO users(login, password) VALUES (?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, login);
            statement.setString(2, passwordHash);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (!keys.next()) throw new SQLException("Не удалось получить id пользователя");
                return new User(keys.getInt(1), login);
            }
        }
    }

    public Optional<UserRecord> findByLogin(String login) throws SQLException {
        String sql = "SELECT id, login, password FROM users WHERE LOWER(login) = LOWER(?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) return Optional.empty();
                return Optional.of(new UserRecord(result.getInt("id"), result.getString("login"),
                        result.getString("password")));
            }
        }
    }

    public record UserRecord(int id, String login, String passwordHash) {
        public User asUser() { return new User(id, login); }
    }
}
