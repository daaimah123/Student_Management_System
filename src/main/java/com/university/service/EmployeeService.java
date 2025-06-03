package com.university.service;

import com.university.dao.EmployeeDAO;
import com.university.dao.impl.EmployeeDAOImpl;
import com.university.model.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAOImpl();
    }

    public void addEmployee(Employee employee) throws SQLException {
        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty() ||
            employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name and last name cannot be empty.");
        }
        // I should add more validation for position, department, etc. when I get a chance
        employeeDAO.addEmployee(employee);
    }

    public Employee getEmployee(int id) throws SQLException {
        return employeeDAO.getEmployee(id);
    }

    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDAO.getAllEmployees();
    }

    public void updateEmployee(Employee employee) throws SQLException {
         if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty() ||
            employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name and last name cannot be empty.");
        }
        employeeDAO.updateEmployee(employee);
    }

    public void deleteEmployee(int id) throws SQLException {
        employeeDAO.deleteEmployee(id);
    }
}
