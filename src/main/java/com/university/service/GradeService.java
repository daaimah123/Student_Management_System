package com.university.service;

import com.university.dao.GradeDAO;
import com.university.dao.impl.GradeDAOImpl;
import com.university.model.Grade;
import com.university.model.Enrollment;

import java.sql.SQLException;
import java.util.List;

public class GradeService {
    private GradeDAO gradeDAO;

    public GradeService() {
        this.gradeDAO = new GradeDAOImpl();
    }

    public void addGrade(Enrollment enrollment, String gradeValue, String comments) throws SQLException {
        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment cannot be null for grading.");
        }
        if (gradeValue == null || gradeValue.trim().isEmpty()) {
            throw new IllegalArgumentException("Grade value cannot be empty.");
        }
        // I'm checking if a grade already exists for this enrollment to prevent duplicates
        Grade existingGrade = gradeDAO.getGradeByEnrollment(enrollment.getId());
        if (existingGrade != null) {
            throw new IllegalArgumentException("A grade already exists for this enrollment. Update it instead.");
        }

        Grade grade = new Grade(0, enrollment, gradeValue, comments); // ID will be set by DAO
        gradeDAO.addGrade(grade);
    }

    public Grade getGrade(int id) throws SQLException {
        return gradeDAO.getGrade(id);
    }
    
    public Grade getGradeByEnrollment(int enrollmentId) throws SQLException {
        return gradeDAO.getGradeByEnrollment(enrollmentId);
    }

    public List<Grade> getAllGrades() throws SQLException {
        return gradeDAO.getAllGrades();
    }

    public void updateGrade(Grade grade) throws SQLException {
        if (grade.getEnrollment() == null) {
            throw new IllegalArgumentException("Enrollment cannot be null for grading.");
        }
        if (grade.getGradeValue() == null || grade.getGradeValue().trim().isEmpty()) {
            throw new IllegalArgumentException("Grade value cannot be empty.");
        }
        gradeDAO.updateGrade(grade);
    }

    public void deleteGrade(int id) throws SQLException {
        gradeDAO.deleteGrade(id);
    }
}
