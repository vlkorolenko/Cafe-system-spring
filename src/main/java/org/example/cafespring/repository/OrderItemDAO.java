package org.example.cafespring.repository;

import org.example.cafespring.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderItemDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderItemDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<OrderItem> findByOrderId(int orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        return jdbcTemplate.query(sql, new Object[]{orderId}, new RowMapper<OrderItem>() {
            @Override
            public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new OrderItem(
                        rs.getInt("order_item_id"),
                        rs.getInt("order_id"),
                        rs.getInt("dish_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
            }
        });
    }

    public void add(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (order_id, dish_id, quantity, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getDishId(), orderItem.getQuantity(), orderItem.getPrice());
    }

    public void update(OrderItem orderItem) {
        String sql = "UPDATE order_items SET dish_id = ?, quantity = ?, price = ? WHERE order_item_id = ?";
        jdbcTemplate.update(sql, orderItem.getDishId(), orderItem.getQuantity(), orderItem.getPrice(), orderItem.getOrderItemId());
    }

    public void delete(int orderItemId) {
        String sql = "DELETE FROM order_items WHERE order_item_id = ?";
        jdbcTemplate.update(sql, orderItemId);
    }
}
