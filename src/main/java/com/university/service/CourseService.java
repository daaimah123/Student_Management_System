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
        courseDAO.deleteCourse(id);
    }
}
