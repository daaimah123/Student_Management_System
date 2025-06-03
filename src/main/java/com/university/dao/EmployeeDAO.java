package com.university.dao;

import com.university.model.Employee;
import java.util.List;
import java.sql.SQLException;

public interface EmployeeDAO {
    void addEmployee(Employee employee) throws SQLException;
    Employee getEmployee(int id) throws SQLException;
    List<Employee> getAllEmployees() throws SQLException;
    void updateEmployee(Employee employee) throws SQLException;
    void deleteEmployee(int id) throws SQLException;
}
