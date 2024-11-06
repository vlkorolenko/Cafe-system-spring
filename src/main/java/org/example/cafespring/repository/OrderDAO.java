package org.example.cafespring.repository;

import org.example.cafespring.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Order> findAll() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Order(
                        rs.getInt("order_id"),
                        rs.getInt("client_id"),
                        rs.getInt("employee_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("total_price"),
                        null // OrderItems буде завантажено окремо
                );
            }
        });
    }

    public int add(Order order) {
        String sql = "INSERT INTO orders (client_id, employee_id, order_date, total_price) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getClientId());
            ps.setInt(2, order.getEmployeeId());
            ps.setDate(3, java.sql.Date.valueOf(order.getOrderDate()));
            ps.setDouble(4, order.getTotalPrice());
            return ps;
        }, keyHolder);

        // Отримання згенерованого order_id з результатів
        Map<String, Object> keys = keyHolder.getKeys();
        return (Integer) keys.get("order_id");
    }

    public void update(Order order) {
        String sql = "UPDATE orders SET client_id = ?, employee_id = ?, order_date = ?, total_price = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, order.getClientId(), order.getEmployeeId(), java.sql.Date.valueOf(order.getOrderDate()), order.getTotalPrice(), order.getOrderId());
    }

    public void delete(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        jdbcTemplate.update(sql, orderId);
    }

    public Order findById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{orderId}, new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Order(
                        rs.getInt("order_id"),
                        rs.getInt("client_id"),
                        rs.getInt("employee_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("total_price"),
                        null // OrderItems буде завантажено окремо
                );
            }
        });
    }
}
