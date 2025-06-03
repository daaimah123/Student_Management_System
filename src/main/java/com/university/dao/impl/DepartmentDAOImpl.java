package com.university.dao.impl;

import com.university.dao.DepartmentDAO;
import com.university.model.Department;
import com.university.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO {

    @Override
    public void addDepartment(Department department) throws SQLException {
        String sql = "INSERT INTO departments (name) VALUES (?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, department.getName());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    department.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Department getDepartment(int id) throws SQLException {
        String sql = "SELECT * FROM departments WHERE id = ?";
        Department department = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    department = new Department(rs.getInt("id"), rs.getString("name"));
                }
            }
        }
        return department;
    }

    @Override
    public List<Department> getAllDepartments() throws SQLException {
        String sql = "SELECT * FROM departments ORDER BY name";
        List<Department> departments = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                departments.add(new Department(rs.getInt("id"), rs.getString("name")));
            }
        }
        return departments;
    }

    @Override
    public void updateDepartment(Department department) throws SQLException {
        String sql = "UPDATE departments SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, department.getName());
            pstmt.setInt(2, department.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteDepartment(int id) throws SQLException {
        String sql = "DELETE FROM departments WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
