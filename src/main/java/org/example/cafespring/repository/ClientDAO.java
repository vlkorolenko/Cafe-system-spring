package org.example.cafespring.repository;

import org.example.cafespring.model.Client;
import org.example.cafespring.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Client> findAll() {
        String sql = "select * from clients";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Client(
                rs.getInt("client_id"),
                rs.getString("full_name"),
                rs.getDate("date_of_birth").toLocalDate()
        ));
    }

    public void add(Client client) {
        String sql = "insert into clients (full_name, date_of_birth) values (?, ?)";
        jdbcTemplate.update(sql, client.getFull_name(), client.getBirth_date());
    }

    public void delete(int id) {
        String sql = "delete from clients where client_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void update(Client client) {
        String sql = "update clients set full_name = ?, date_of_birth = ? where client_id = ?";
        jdbcTemplate.update(sql, client.getFull_name(), client.getBirth_date(), client.getId());
    }

    public Client findById(int id) {
        String sql = "SELECT * FROM clients WHERE client_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Client>() {
            @Override
            public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
                Client client = new Client();
                client.setId(rs.getInt("client_id"));
                client.setFull_name(rs.getString("full_name"));
                client.setBirth_date(rs.getDate("date_of_birth").toLocalDate());
                return client;
            }
        });
    }
}
