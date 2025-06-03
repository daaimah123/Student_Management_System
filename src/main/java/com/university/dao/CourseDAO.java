package com.university.dao;

import com.university.model.Course;
import java.util.List;
import java.sql.SQLException;

public interface CourseDAO {
    void addCourse(Course course) throws SQLException;
    Course getCourse(int id) throws SQLException;
    List<Course> getAllCourses() throws SQLException;
    void updateCourse(Course course) throws SQLException;
    void deleteCourse(int id) throws SQLException;
}
