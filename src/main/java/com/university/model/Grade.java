package com.university.model;

public class Grade {
    private int id;
    private Enrollment enrollment;
    private String gradeValue; // I'm keeping this flexible - could be "A", "B+", "3.5", etc.
    private String comments;

    private int enrollmentId; // For database operations

    public Grade() {}

    public Grade(int id, Enrollment enrollment, String gradeValue, String comments) {
        this.id = id;
        this.enrollment = enrollment;
        this.gradeValue = gradeValue;
        this.comments = comments;
        if (enrollment != null) this.enrollmentId = enrollment.getId();
    }

    public Grade(int id, int enrollmentId, String gradeValue, String comments) {
        this.id = id;
        this.enrollmentId = enrollmentId;
        this.gradeValue = gradeValue;
        this.comments = comments;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Enrollment getEnrollment() { return enrollment; }
    public void setEnrollment(Enrollment enrollment) { 
        this.enrollment = enrollment; 
        if (enrollment != null) this.enrollmentId = enrollment.getId();
    }
    public String getGradeValue() { return gradeValue; }
    public void setGradeValue(String gradeValue) { this.gradeValue = gradeValue; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }

    @Override
    public String toString() {
        return "Grade for " + (enrollment != null ? enrollment.toString() : "N/A") + ": " + gradeValue;
    }
}
