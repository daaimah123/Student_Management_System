package com.university.dao;

import com.university.model.Grade;
import java.util.List;
import java.sql.SQLException;

public interface GradeDAO {
    void addGrade(Grade grade) throws SQLException;
    Grade getGrade(int id) throws SQLException;
    Grade getGradeByEnrollment(int enrollmentId) throws SQLException;
    List<Grade> getAllGrades() throws SQLException; // This might be a lot of data, but useful for reports
    void updateGrade(Grade grade) throws SQLException;
    void deleteGrade(int id) throws SQLException; // Could also delete by enrollmentId if needed
}
