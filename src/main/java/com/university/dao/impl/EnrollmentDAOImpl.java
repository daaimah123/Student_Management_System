package com.university.dao.impl;

import com.university.dao.EnrollmentDAO;
import com.university.dao.StudentDAO;
import com.university.dao.CourseDAO;
import com.university.model.Enrollment;
import com.university.model.Student;
import com.university.model.Course;
import com.university.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAOImpl implements EnrollmentDAO {
    private StudentDAO studentDAO = new StudentDAOImpl(); // I need these to build complete Enrollment objects
    private CourseDAO courseDAO = new CourseDAOImpl();

    @Override
    public void addEnrollment(Enrollment enrollment) throws SQLException {
        String sql = "INSERT INTO enrollments (studentId, courseId, enrollmentDate) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, enrollment.getStudent().getId());
            pstmt.setInt(2, enrollment.getCourse().getId());
            pstmt.setString(3, enrollment.getEnrollmentDate());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    enrollment.setId(generatedKeys.getInt(1));
                }
            }
        }
    }
    
    @Override
    public boolean isStudentEnrolled(int studentId, int courseId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM enrollments WHERE studentId = ? AND courseId = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // I'm checking if any enrollments exist for this combo
                }
            }
        }
        return false;
    }

    private Enrollment mapResultSetToEnrollment(ResultSet rs) throws SQLException {
        // I'm extracting this to avoid code duplication - learned this pattern from experience
        Student student = studentDAO.getStudent(rs.getInt("studentId"));
        Course course = courseDAO.getCourse(rs.getInt("courseId"));
        return new Enrollment(
                rs.getInt("id"),
                student,
                course,
                rs.getString("enrollmentDate")
        );
    }
    
    @Override
    public Enrollment getEnrollment(int id) throws SQLException {
        String sql = "SELECT * FROM enrollments WHERE id = ?";
        Enrollment enrollment = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    enrollment = mapResultSetToEnrollment(rs);
                }
            }
        }
        return enrollment;
    }

    @Override
    public List<Enrollment> getAllEnrollments() throws SQLException {
        String sql = "SELECT * FROM enrollments";
        List<Enrollment> enrollments = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                enrollments.add(mapResultSetToEnrollment(rs));
            }
        }
        return enrollments;
    }
    
    @Override
    public List<Enrollment> getEnrollmentsByStudent(int studentId) throws SQLException {
        String sql = "SELECT * FROM enrollments WHERE studentId = ?";
        List<Enrollment> enrollments = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    enrollments.add(mapResultSetToEnrollment(rs));
                }
            }
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> getEnrollmentsByCourse(int courseId) throws SQLException {
         String sql = "SELECT * FROM enrollments WHERE courseId = ?";
        List<Enrollment> enrollments = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    enrollments.add(mapResultSetToEnrollment(rs));
                }
            }
        }
        return enrollments;
    }

    @Override
    public void updateEnrollment(Enrollment enrollment) throws SQLException {
        String sql = "UPDATE enrollments SET studentId = ?, courseId = ?, enrollmentDate = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, enrollment.getStudent().getId());
            pstmt.setInt(2, enrollment.getCourse().getId());
            pstmt.setString(3, enrollment.getEnrollmentDate());
            pstmt.setInt(4, enrollment.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteEnrollment(int id) throws SQLException {
        String sql = "DELETE FROM enrollments WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
