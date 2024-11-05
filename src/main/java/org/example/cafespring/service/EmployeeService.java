package org.example.cafespring.service;

import org.example.cafespring.model.Employee;
import org.example.cafespring.repository.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    public void addEmployee(Employee employee) {
        employeeDAO.add(employee);
    }

    public void deleteEmployee(int id) {
        employeeDAO.delete(id);
    }

    public Employee findEmployee(int id) {
        return employeeDAO.findById(id);
    }

    public void updateEmployee(Employee employee) {
        employeeDAO.update(employee);
    }
}
