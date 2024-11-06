package org.example.cafespring.repository;

import org.example.cafespring.model.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FavoriteDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FavoriteDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Favorite> findAll() {
        String sql = "SELECT * FROM favorites";
        return jdbcTemplate.query(sql, new RowMapper<Favorite>() {
            @Override
            public Favorite mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Favorite(rs.getInt("client_id"), rs.getInt("dish_id"));
            }
        });
    }

    public void add(Favorite favorite) {
        String sql = "INSERT INTO favorites (client_id, dish_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, favorite.getClientId(), favorite.getDishId());
    }

    public void delete(int clientId, int dishId) {
        String sql = "DELETE FROM favorites WHERE client_id = ? AND dish_id = ?";
        jdbcTemplate.update(sql, clientId, dishId);
    }

    public void update(Favorite favorite) {
        String sql = "UPDATE favorites SET dish_id = ? WHERE client_id = ? AND dish_id = ?";
        jdbcTemplate.update(sql, favorite.getDishId(), favorite.getClientId(), favorite.getDishId());
    }

    public Favorite findByIds(int clientId, int dishId) {
        String sql = "SELECT * FROM favorites WHERE client_id = ? AND dish_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{clientId, dishId}, new RowMapper<Favorite>() {
            @Override
            public Favorite mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Favorite(rs.getInt("client_id"), rs.getInt("dish_id"));
            }
        });
    }
}
