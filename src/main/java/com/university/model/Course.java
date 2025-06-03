package com.university.model;

public class Course {
    private int id;
    private String courseCode;
    private String courseName;
    private int credits;
    private Department department; // Full Department object for display purposes
    private int departmentId; // ID for database storage

    public Course() {}

    public Course(int id, String courseCode, String courseName, int credits, Department department) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.department = department;
        if (department != null) {
            this.departmentId = department.getId();
        }
    }
    
    public Course(int id, String courseCode, String courseName, int credits, int departmentId) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.departmentId = departmentId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { 
        this.department = department; 
        if (department != null) this.departmentId = department.getId();
    }
    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    @Override
    public String toString() {
        return courseCode + " - " + courseName; // This makes the course display nicely in dropdowns
    }
}
