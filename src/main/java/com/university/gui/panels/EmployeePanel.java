package com.university.gui.panels;

import com.university.model.Employee;
import com.university.model.Department;
import com.university.service.EmployeeService;
import com.university.service.DepartmentService;
import com.university.gui.dialogs.EmployeeDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class EmployeePanel extends JPanel {
    private EmployeeService employeeService;
    private DepartmentService departmentService; // Needed for department names
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public EmployeePanel() {
        employeeService = new EmployeeService();
        departmentService = new DepartmentService(); // Initialize
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "First Name", "Last Name", "Email", "Date of Birth", "Position", "Department"};
        tableModel = new DefaultTableModel(columnNames, 0) {
             @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Employee");
        JButton editButton = new JButton("Edit Employee");
        JButton deleteButton = new JButton("Delete Employee");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadEmployees();

        addButton.addActionListener(this::addEmployeeAction);
        editButton.addActionListener(this::editEmployeeAction);
        deleteButton.addActionListener(this::deleteEmployeeAction);
        refreshButton.addActionListener(e -> loadEmployees());
    }

    private void loadEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            tableModel.setRowCount(0);
            for (Employee emp : employees) {
                String departmentName = emp.getDepartment() != null ? emp.getDepartment().getName() : "N/A";
                tableModel.addRow(new Object[]{
                        emp.getId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getEmail(),
                        emp.getDateOfBirth(),
                        emp.getPosition(),
                        departmentName
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading employees: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEmployeeAction(ActionEvent e) {
        EmployeeDialog dialog = new EmployeeDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Employee", null, employeeService, departmentService);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            loadEmployees();
        }
    }

    private void editEmployeeAction(ActionEvent e) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            int empId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                Employee empToEdit = employeeService.getEmployee(empId);
                if (empToEdit != null) {
                    EmployeeDialog dialog = new EmployeeDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Employee", empToEdit, employeeService, departmentService);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        loadEmployees();
                    }
                } else {
                     JOptionPane.showMessageDialog(this, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error fetching employee: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteEmployeeAction(ActionEvent e) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            int empId = (int) tableModel.getValueAt(selectedRow, 0);
            String empName = tableModel.getValueAt(selectedRow, 1) + " " + tableModel.getValueAt(selectedRow, 2);
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete employee: " + empName + " (ID: " + empId + ")?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    employeeService.deleteEmployee(empId);
                    loadEmployees();
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting employee: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}
