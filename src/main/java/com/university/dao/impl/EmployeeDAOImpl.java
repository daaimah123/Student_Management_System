package com.university.dao.impl;

import com.university.dao.EmployeeDAO;
import com.university.dao.DepartmentDAO; // I need this to fetch Department objects
import com.university.model.Employee;
import com.university.model.Department;
import com.university.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private DepartmentDAO departmentDAO = new DepartmentDAOImpl(); // I'm using this to fetch department details

    @Override
    public void addEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees (firstName, lastName, email, dateOfBirth, position, departmentId) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getDateOfBirth());
            pstmt.setString(5, employee.getPosition());
            if (employee.getDepartment() != null) {
                pstmt.setInt(6, employee.getDepartment().getId());
            } else if (employee.getDepartmentId() > 0) {
                 pstmt.setInt(6, employee.getDepartmentId());
            }
            else {
                pstmt.setNull(6, Types.INTEGER); // Some employees might not have a department assigned yet
            }
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Employee getEmployee(int id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id = ?";
        Employee employee = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Department department = null;
                    int departmentId = rs.getInt("departmentId");
                    if (!rs.wasNull()) { // I need to check if departmentId was actually NULL in the database
                        department = departmentDAO.getDepartment(departmentId);
                    }
                    employee = new Employee(
                            rs.getInt("id"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("email"),
                            rs.getString("dateOfBirth"),
                            rs.getString("position"),
                            department // Passing the full Department object
                    );
                }
            }
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        String sql = "SELECT * FROM employees ORDER BY lastName, firstName"; // Consistent sorting with students
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                 Department department = null;
                 int departmentId = rs.getInt("departmentId");
                 if (!rs.wasNull()) {
                     department = departmentDAO.getDepartment(departmentId);
                 }
                 employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("dateOfBirth"),
                        rs.getString("position"),
                        department
                ));
            }
        }
        return employees;
    }

    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE employees SET firstName = ?, lastName = ?, email = ?, dateOfBirth = ?, position = ?, departmentId = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getDateOfBirth());
            pstmt.setString(5, employee.getPosition());
            if (employee.getDepartment() != null) {
                pstmt.setInt(6, employee.getDepartment().getId());
            } else if (employee.getDepartmentId() > 0) {
                 pstmt.setInt(6, employee.getDepartmentId());
            }
             else {
                pstmt.setNull(6, Types.INTEGER);
            }
            pstmt.setInt(7, employee.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
