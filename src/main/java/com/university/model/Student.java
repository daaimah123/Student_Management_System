package com.university.model;

public class Student extends Person {
    // Student-specific fields can be added here if needed
    // For now, it inherits all fields from Person

    public Student() {
        super();
    }

    public Student(int id, String firstName, String lastName, String email, String dateOfBirth) {
        super(id, firstName, lastName, email, dateOfBirth);
    }
}
