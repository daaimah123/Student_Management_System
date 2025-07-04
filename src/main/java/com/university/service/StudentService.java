package com.university.service;

import com.university.dao.StudentDAO;
import com.university.dao.impl.StudentDAOImpl;
import com.university.model.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAOImpl();
    }

    public void addStudent(Student student) throws SQLException {
        // TODO: Add email format validation
        // TODO: Add date of birth validation (reasonable age range, valid date format)
        // TODO: Check for duplicate email addresses
        // I'm doing basic validation here - should expand this later
        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty() ||
            student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name and last name cannot be empty.");
        }
        studentDAO.addStudent(student);
    }

    public Student getStudent(int id) throws SQLException {
        return studentDAO.getStudent(id);
    }

    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    public void updateStudent(Student student) throws SQLException {
        // TODO: Add comprehensive validation (same as addStudent)
        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty() ||
            student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name and last name cannot be empty.");
        }
        studentDAO.updateStudent(student);
    }

    public void deleteStudent(int id) throws SQLException {
        // TODO: Handle student enrollments before deletion (unenroll or prevent deletion)
        // TODO: Handle student grades before deletion
        // I should consider business logic here: maybe unenroll from courses before deleting
        studentDAO.deleteStudent(id);
    }
}
