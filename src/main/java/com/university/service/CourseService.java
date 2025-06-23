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
        // Check for duplicate course codes
        if(getAllCourses().stream().anyMatch(e -> course.getCourseCode().equals(e.getCourseCode())))
            throw new IllegalArgumentException("Entered Course Code already exists");
       
        courseDAO.addCourse(course);
    }

    public Course getCourse(int id) throws SQLException {
        return courseDAO.getCourse(id);
    }

    public List<Course> getAllCourses() throws SQLException {
        return courseDAO.getAllCourses();
    }

    public void updateCourse(Course course) throws SQLException {
        // Check for duplicate course codes (excluding current course)
        if(getAllCourses().stream().anyMatch(e -> course.getCourseCode().equals(e.getCourseCode()) && course.getId() != e.getId()))
            throw new IllegalArgumentException("Entered Course Code already exists");

        courseDAO.updateCourse(course);
    }

    public void deleteCourse(int id) throws SQLException {
        // TODO: Check for existing enrollments before deletion
        // TODO: Provide option to handle existing enrollments (transfer or cancel)
        courseDAO.deleteCourse(id);
    }
}
