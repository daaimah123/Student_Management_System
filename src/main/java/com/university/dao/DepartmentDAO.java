package com.university.dao;

import com.university.model.Department;
import java.util.List;
import java.sql.SQLException;

public interface DepartmentDAO {
    void addDepartment(Department department) throws SQLException;
    Department getDepartment(int id) throws SQLException;
    List<Department> getAllDepartments() throws SQLException;
    void updateDepartment(Department department) throws SQLException;
    void deleteDepartment(int id) throws SQLException;
}
