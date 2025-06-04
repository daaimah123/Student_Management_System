package com.university.service;

import com.university.dao.DepartmentDAO;
import com.university.dao.impl.DepartmentDAOImpl;
import com.university.model.Department;

import java.sql.SQLException;
import java.util.List;

public class DepartmentService {
    private DepartmentDAO departmentDAO;

    public DepartmentService() {
        this.departmentDAO = new DepartmentDAOImpl();
    }

    public void addDepartment(Department department) throws SQLException {
        // TODO: Add validation for department name (not null, not empty, reasonable length)
        // TODO: Check for duplicate department names
        // I should add more validation here when I have time
        departmentDAO.addDepartment(department);
    }

    public Department getDepartment(int id) throws SQLException {
        return departmentDAO.getDepartment(id);
    }

    public List<Department> getAllDepartments() throws SQLException {
        return departmentDAO.getAllDepartments();
    }

    public void updateDepartment(Department department) throws SQLException {
        // TODO: Add validation for department name
        // TODO: Check for duplicate department names (excluding current department)
        // I should add validation here too
        departmentDAO.updateDepartment(department);
    }

    public void deleteDepartment(int id) throws SQLException {
        // TODO: Check if department has employees or courses before deletion
        // TODO: Provide option to reassign employees/courses to another department
        // I need to handle cascading deletes or checks for employees/courses in this department
        departmentDAO.deleteDepartment(id);
    }
}
