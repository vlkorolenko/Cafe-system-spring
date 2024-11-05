package org.example.cafespring.repository;

import org.example.cafespring.model.Client;
import org.example.cafespring.model.Dish;
import org.example.cafespring.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> findAll() {
        String sql = "select * from employees";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Employee(
                rs.getInt("employee_id"),
                rs.getString("full_name"),
                rs.getDate("date_of_birth").toLocalDate()
        ));
    }

    public void add(Employee employee) {
        String sql = "insert into employees (full_name, date_of_birth) values (?, ?)";
        jdbcTemplate.update(sql, employee.getFull_name(), employee.getDate_of_birth());
    }

    public void delete(int employee_id) {
        String sql = "delete from employees where employee_id = ?";
        jdbcTemplate.update(sql, employee_id);
    }

    public Employee findById(int id) {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                Employee dish = new Employee();
                dish.setId(rs.getInt("employee_id"));
                dish.setFull_name(rs.getString("full_name"));
                dish.setDate_of_birth(rs.getDate("date_of_birth").toLocalDate());
                return dish;
            }
        });
    }

    public void update(Employee employee) {
        String sql = "update employees set full_name = ?, date_of_birth = ? where employee_id = ?";
        jdbcTemplate.update(sql, employee.getFull_name(), employee.getDate_of_birth(), employee.getId());
    }
}
