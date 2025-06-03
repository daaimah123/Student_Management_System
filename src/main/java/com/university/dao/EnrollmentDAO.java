package com.university.dao;

import com.university.model.Enrollment;
import java.util.List;
import java.sql.SQLException;

public interface EnrollmentDAO {
    void addEnrollment(Enrollment enrollment) throws SQLException;
    Enrollment getEnrollment(int id) throws SQLException;
    List<Enrollment> getAllEnrollments() throws SQLException;
    List<Enrollment> getEnrollmentsByStudent(int studentId) throws SQLException;
    List<Enrollment> getEnrollmentsByCourse(int courseId) throws SQLException;
    void updateEnrollment(Enrollment enrollment) throws SQLException; // Usually just date
    void deleteEnrollment(int id) throws SQLException;
    boolean isStudentEnrolled(int studentId, int courseId) throws SQLException;
}
