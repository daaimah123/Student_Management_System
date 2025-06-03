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
        // Add validation if needed
        departmentDAO.addDepartment(department);
    }

    public Department getDepartment(int id) throws SQLException {
        return departmentDAO.getDepartment(id);
    }

    public List<Department> getAllDepartments() throws SQLException {
        return departmentDAO.getAllDepartments();
    }

    public void updateDepartment(Department department) throws SQLException {
        // Add validation if needed
        departmentDAO.updateDepartment(department);
    }

    public void deleteDepartment(int id) throws SQLException {
        // Add business logic for cascading deletes or checks if needed
        departmentDAO.deleteDepartment(id);
    }
}
