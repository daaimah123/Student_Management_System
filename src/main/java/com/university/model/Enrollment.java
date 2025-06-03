package com.university.model;

public class Enrollment {
    private int id;
    private Student student;
    private Course course;
    private String enrollmentDate; // Consider LocalDate

    private int studentId; // for DB
    private int courseId; // for DB

    public Enrollment() {}

    public Enrollment(int id, Student student, Course course, String enrollmentDate) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        if (student != null) this.studentId = student.getId();
        if (course != null) this.courseId = course.getId();
    }
    
    public Enrollment(int id, int studentId, int courseId, String enrollmentDate) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { 
        this.student = student; 
        if (student != null) this.studentId = student.getId();
    }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { 
        this.course = course; 
        if (course != null) this.courseId = course.getId();
    }
    public String getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(String enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    @Override
    public String toString() {
        return (student != null ? student.toString() : "N/A") + " enrolled in " + (course != null ? course.toString() : "N/A");
    }
}
