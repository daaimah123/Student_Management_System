package com.university.dao.impl;

import com.university.dao.CourseDAO;
import com.university.dao.DepartmentDAO;
import com.university.model.Course;
import com.university.model.Department;
import com.university.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {
    private DepartmentDAO departmentDAO = new DepartmentDAOImpl(); // I need this for department lookups

    @Override
    public void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO courses (courseCode, courseName, credits, departmentId) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, course.getCourseCode());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCredits());
            if (course.getDepartment() != null) {
                pstmt.setInt(4, course.getDepartment().getId());
            } else if (course.getDepartmentId() > 0) {
                pstmt.setInt(4, course.getDepartmentId());
            }
            else {
                pstmt.setNull(4, Types.INTEGER); // Some courses might be interdisciplinary
            }
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    course.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Course getCourse(int id) throws SQLException {
        String sql = "SELECT * FROM courses WHERE id = ?";
        Course course = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Department department = null;
                    int departmentId = rs.getInt("departmentId");
                    if (!rs.wasNull()) {
                        department = departmentDAO.getDepartment(departmentId);
                    }
                    course = new Course(
                            rs.getInt("id"),
                            rs.getString("courseCode"),
                            rs.getString("courseName"),
                            rs.getInt("credits"),
                            department
                    );
                }
            }
        }
        return course;
    }

    @Override
    public List<Course> getAllCourses() throws SQLException {
        String sql = "SELECT * FROM courses ORDER BY courseName"; // I'm sorting by course name for easier browsing
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Department department = null;
                int departmentId = rs.getInt("departmentId");
                if (!rs.wasNull()) {
                    department = departmentDAO.getDepartment(departmentId);
                }
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("courseCode"),
                        rs.getString("courseName"),
                        rs.getInt("credits"),
                        department
                ));
            }
        }
        return courses;
    }

    @Override
    public void updateCourse(Course course) throws SQLException {
        String sql = "UPDATE courses SET courseCode = ?, courseName = ?, credits = ?, departmentId = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseCode());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCredits());
            if (course.getDepartment() != null) {
                pstmt.setInt(4, course.getDepartment().getId());
            } else if (course.getDepartmentId() > 0) {
                pstmt.setInt(4, course.getDepartmentId());
            }
             else {
                pstmt.setNull(4, Types.INTEGER);
            }
            pstmt.setInt(5, course.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteCourse(int id) throws SQLException {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
