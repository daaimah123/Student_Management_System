package com.university.dao;

import com.university.model.Grade;
import java.util.List;
import java.sql.SQLException;

public interface GradeDAO {
    void addGrade(Grade grade) throws SQLException;
    Grade getGrade(int id) throws SQLException;
    Grade getGradeByEnrollment(int enrollmentId) throws SQLException;
    List<Grade> getAllGrades() throws SQLException; // Might be too broad, consider specific queries
    void updateGrade(Grade grade) throws SQLException;
    void deleteGrade(int id) throws SQLException; // Or by enrollmentId
}
