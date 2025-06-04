package com.university.service;

import com.university.dao.EnrollmentDAO;
import com.university.dao.impl.EnrollmentDAOImpl;
import com.university.model.Enrollment;
import com.university.model.Student;
import com.university.model.Course;

import java.sql.SQLException;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EnrollmentService {
    private EnrollmentDAO enrollmentDAO;

    public EnrollmentService() {
        this.enrollmentDAO = new EnrollmentDAOImpl();
    }

    public void addEnrollment(Student student, Course course) throws SQLException {
        // TODO: Add enrollment capacity checks for courses
        // TODO: Add prerequisite validation
        // TODO: Add enrollment period validation (registration windows)
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and Course cannot be null for enrollment.");
        }
        if (enrollmentDAO.isStudentEnrolled(student.getId(), course.getId())) {
            throw new IllegalArgumentException("Student is already enrolled in this course.");
        }
        
        // TODO: Make date handling consistent throughout the application
        String currentDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE); // I'm auto-setting today's date
        Enrollment enrollment = new Enrollment(0, student, course, currentDate); // ID will be set by DAO
        enrollmentDAO.addEnrollment(enrollment);
    }

    public Enrollment getEnrollment(int id) throws SQLException {
        return enrollmentDAO.getEnrollment(id);
    }

    public List<Enrollment> getAllEnrollments() throws SQLException {
        return enrollmentDAO.getAllEnrollments();
    }
    
    public List<Enrollment> getEnrollmentsByStudent(int studentId) throws SQLException {
        return enrollmentDAO.getEnrollmentsByStudent(studentId);
    }

    public List<Enrollment> getEnrollmentsByCourse(int courseId) throws SQLException {
        return enrollmentDAO.getEnrollmentsByCourse(courseId);
    }

    public void updateEnrollment(Enrollment enrollment) throws SQLException {
        // Usually only the enrollment date might be updatable in real scenarios
        enrollmentDAO.updateEnrollment(enrollment);
    }

    public void deleteEnrollment(int id) throws SQLException {
        // TODO: Handle associated grade deletion before removing enrollment
        // TODO: Add business rules for enrollment deletion (e.g., deadline restrictions)
        // I should handle the associated grade deletion first, but keeping it simple for now
        enrollmentDAO.deleteEnrollment(id);
    }
}
