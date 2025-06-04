package com.university.model;

public class Student extends Person {
    // TODO: Consider adding student-specific fields like GPA, major, student ID number, enrollment status
    // I'm keeping this simple for now - could add student-specific fields later like GPA, major, etc.

    public Student() {
        super();
    }

    public Student(int id, String firstName, String lastName, String email, String dateOfBirth) {
        super(id, firstName, lastName, email, dateOfBirth);
    }
}
