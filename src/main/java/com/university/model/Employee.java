package com.university.model;

public class Employee extends Person {
    private String position;
    private Department department; // Reference to Department object
    private int departmentId; // For database storage

    public Employee() {
        super();
    }

    public Employee(int id, String firstName, String lastName, String email, String dateOfBirth, String position, Department department) {
        super(id, firstName, lastName, email, dateOfBirth);
        this.position = position;
        this.department = department;
        if (department != null) {
            this.departmentId = department.getId();
        }
    }
    
    public Employee(int id, String firstName, String lastName, String email, String dateOfBirth, String position, int departmentId) {
        super(id, firstName, lastName, email, dateOfBirth);
        this.position = position;
        this.departmentId = departmentId;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
        if (department != null) {
            this.departmentId = department.getId();
        } else {
            this.departmentId = 0; // Or handle as appropriate
        }
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
