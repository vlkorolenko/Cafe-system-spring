package org.example.cafespring.repository;

import org.example.cafespring.model.Employee;
import org.example.cafespring.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProductDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        String sql = "select * from products";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Product(
                rs.getInt("product_id"),
                rs.getString("name"),
                rs.getDate("expiry_date").toLocalDate()
        ));
    }

    public void add(Product product) {
        String sql = "insert into products (name, expiry_date) values (?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getExpiryDate());
    }

    public void delete(int id) {
        String sql = "delete from products where product_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void update(Product product) {
        String sql = "update products set name = ?, expiry_date = ? where product_id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getExpiryDate(), product.getId());
    }

    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setExpiryDate(rs.getDate("expiry_date").toLocalDate());
                return product;
            }
        });
    }
}
