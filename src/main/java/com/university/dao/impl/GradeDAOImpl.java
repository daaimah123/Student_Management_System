package com.university.dao.impl;

import com.university.dao.GradeDAO;
import com.university.dao.EnrollmentDAO;
import com.university.model.Grade;
import com.university.model.Enrollment;
import com.university.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAOImpl implements GradeDAO {
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAOImpl(); // I need this to build complete Grade objects

    private Grade mapResultSetToGrade(ResultSet rs) throws SQLException {
        // Another helper method to avoid repetition
        Enrollment enrollment = enrollmentDAO.getEnrollment(rs.getInt("enrollmentId"));
        return new Grade(
                rs.getInt("id"),
                enrollment,
                rs.getString("gradeValue"),
                rs.getString("comments")
        );
    }

    @Override
    public void addGrade(Grade grade) throws SQLException {
        String sql = "INSERT INTO grades (enrollmentId, gradeValue, comments) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, grade.getEnrollment().getId());
            pstmt.setString(2, grade.getGradeValue());
            pstmt.setString(3, grade.getComments());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    grade.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Grade getGrade(int id) throws SQLException {
        String sql = "SELECT * FROM grades WHERE id = ?";
        Grade grade = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    grade = mapResultSetToGrade(rs);
                }
            }
        }
        return grade;
    }
    
    @Override
    public Grade getGradeByEnrollment(int enrollmentId) throws SQLException {
        String sql = "SELECT * FROM grades WHERE enrollmentId = ?";
        Grade grade = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, enrollmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    grade = mapResultSetToGrade(rs);
                }
            }
        }
        return grade;
    }

    @Override
    public List<Grade> getAllGrades() throws SQLException {
        String sql = "SELECT * FROM grades";
        List<Grade> grades = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                grades.add(mapResultSetToGrade(rs));
            }
        }
        return grades;
    }

    @Override
    public void updateGrade(Grade grade) throws SQLException {
        String sql = "UPDATE grades SET enrollmentId = ?, gradeValue = ?, comments = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, grade.getEnrollment().getId());
            pstmt.setString(2, grade.getGradeValue());
            pstmt.setString(3, grade.getComments());
            pstmt.setInt(4, grade.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteGrade(int id) throws SQLException {
        String sql = "DELETE FROM grades WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
