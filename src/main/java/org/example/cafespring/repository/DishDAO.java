package org.example.cafespring.repository;

import org.example.cafespring.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DishDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DishDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Dish> getAllDishes(){
        String sql = "select * from dishes";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Dish(
                rs.getInt("dish_id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getBigDecimal("price"),
                rs.getDate("expiry_date").toLocalDate()
        ));
    }

    public void addDish(Dish dish) {
        String sql = "INSERT INTO dishes (name, type, price, expiry_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, dish.getName(), dish.getType(), dish.getPrice(), dish.getExpiryDate());
    }

    public void deleteDish(int id) {
        String sql = "DELETE FROM dishes WHERE dish_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateDish(Dish dish) {
        String sql = "UPDATE dishes set name = ?, type = ?, price = ?, expiry_date = ? WHERE dish_id = ?";
        jdbcTemplate.update(sql, dish.getName(), dish.getType(), dish.getPrice(), dish.getExpiryDate(), dish.getId());
    }

    public Dish findById(int id) {
        String sql = "SELECT * FROM dishes WHERE dish_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Dish>() {
            @Override
            public Dish mapRow(ResultSet rs, int rowNum) throws SQLException {
                Dish dish = new Dish();
                dish.setId(rs.getInt("dish_id"));
                dish.setName(rs.getString("name"));
                dish.setType(rs.getString("type"));
                dish.setPrice(rs.getBigDecimal("price"));
                dish.setExpiryDate(rs.getDate("expiry_date").toLocalDate());
                return dish;
            }
        });
    }


}
