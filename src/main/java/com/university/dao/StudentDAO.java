package com.university.dao;

import com.university.model.Student;
import java.util.List;
import java.sql.SQLException;

public interface StudentDAO {
    void addStudent(Student student) throws SQLException;
    Student getStudent(int id) throws SQLException;
    List<Student> getAllStudents() throws SQLException;
    void updateStudent(Student student) throws SQLException;
    void deleteStudent(int id) throws SQLException;
}
