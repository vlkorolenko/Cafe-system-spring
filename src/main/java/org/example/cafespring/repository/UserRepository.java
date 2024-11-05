package org.example.cafespring.repository;

import org.example.cafespring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Перевіряємо, чи існує користувач з таким логіном
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    // Додаємо нового користувача до бази даних
    public void save(User user) {
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }

    // Отримуємо хеш пароля користувача для перевірки під час авторизації
    public String findPasswordHashByUsername(String username) {
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        List<String> results = jdbcTemplate.query(sql, new Object[]{username},
                (rs, rowNum) -> rs.getString("password_hash"));

        return results.isEmpty() ? null : results.get(0);
    }

}

