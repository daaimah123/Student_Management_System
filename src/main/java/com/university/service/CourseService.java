package com.university.service;

import com.university.dao.CourseDAO;
import com.university.dao.impl.CourseDAOImpl;
import com.university.model.Course;

import java.sql.SQLException;
import java.util.List;

public class CourseService {
    private CourseDAO courseDAO;

    public CourseService() {
        this.courseDAO = new CourseDAOImpl();
    }

    public void addCourse(Course course) throws SQLException {
        // TODO: Add validation for course code format (e.g., CS101, MATH200)
        // TODO: Check for duplicate course codes
        // TODO: Validate credit range (e.g., 1-6 credits)
        if (course.getCourseCode() == null || course.getCourseCode().trim().isEmpty() ||
            course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            throw new IllegalArgumentException("Course code and name cannot be empty.");
        }
        if (course.getCredits() <= 0) {
            throw new IllegalArgumentException("Credits must be positive.");
        }
        courseDAO.addCourse(course);
    }

    public Course getCourse(int id) throws SQLException {
        return courseDAO.getCourse(id);
    }

    public List<Course> getAllCourses() throws SQLException {
        return courseDAO.getAllCourses();
    }

    public void updateCourse(Course course) throws SQLException {
        // TODO: Add same validation as addCourse
        // TODO: Check for duplicate course codes (excluding current course)
        if (course.getCourseCode() == null || course.getCourseCode().trim().isEmpty() ||
            course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            throw new IllegalArgumentException("Course code and name cannot be empty.");
        }
        if (course.getCredits() <= 0) {
            throw new IllegalArgumentException("Credits must be positive.");
        }
        courseDAO.updateCourse(course);
    }

    public void deleteCourse(int id) throws SQLException {
        // TODO: Check for existing enrollments before deletion
        // TODO: Provide option to handle existing enrollments (transfer or cancel)
        courseDAO.deleteCourse(id);
    }
}
