package com.university.model;

public class Employee extends Person {
    private String position;
    private Department department; // I'm storing the full Department object for convenience
    private int departmentId; // But I also need the ID for database operations

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
            this.departmentId = 0; // I'm using 0 to indicate no department for now
        }
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
