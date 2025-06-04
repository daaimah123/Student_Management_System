package com.university.gui.panels;

import com.university.model.Department;
import com.university.service.DepartmentService;
import com.university.gui.dialogs.DepartmentDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class DepartmentPanel extends JPanel {
    private DepartmentService departmentService;
    private JTable departmentTable;
    private DefaultTableModel tableModel;

    public DepartmentPanel() {
        departmentService = new DepartmentService();
        setLayout(new BorderLayout());

        // TODO: Add search functionality for department names
        // TODO: Add department statistics (number of employees, courses)

        String[] columnNames = {"ID", "Department Name"};
        tableModel = new DefaultTableModel(columnNames, 0) {
             @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        departmentTable = new JTable(tableModel);
        departmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(departmentTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Department");
        JButton editButton = new JButton("Edit Department");
        JButton deleteButton = new JButton("Delete Department");
        JButton refreshButton = new JButton("Refresh");

        // TODO: Add "View Details" button to show department employees and courses
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadDepartments();

        addButton.addActionListener(this::addDepartmentAction);
        editButton.addActionListener(this::editDepartmentAction);
        deleteButton.addActionListener(this::deleteDepartmentAction);
        refreshButton.addActionListener(e -> loadDepartments());
    }

    private void loadDepartments() {
        try {
            List<Department> departments = departmentService.getAllDepartments();
            tableModel.setRowCount(0);
            for (Department dept : departments) {
                tableModel.addRow(new Object[]{dept.getId(), dept.getName()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading departments: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addDepartmentAction(ActionEvent e) {
        DepartmentDialog dialog = new DepartmentDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Department", null, departmentService);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            loadDepartments();
        }
    }

    private void editDepartmentAction(ActionEvent e) {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            int deptId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                Department deptToEdit = departmentService.getDepartment(deptId);
                 if (deptToEdit != null) {
                    DepartmentDialog dialog = new DepartmentDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Department", deptToEdit, departmentService);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        loadDepartments();
                    }
                } else {
                     JOptionPane.showMessageDialog(this, "Department not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error fetching department: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a department to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteDepartmentAction(ActionEvent e) {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            int deptId = (int) tableModel.getValueAt(selectedRow, 0);
            String deptName = (String) tableModel.getValueAt(selectedRow, 1);
            
            // TODO: Check for employees and courses in this department before deletion
            // TODO: Provide options to reassign or handle dependent records
            
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete department: " + deptName + " (ID: " + deptId + ")?\n" +
                    "This might affect employees and courses associated with this department.",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    departmentService.deleteDepartment(deptId);
                    loadDepartments();
                    JOptionPane.showMessageDialog(this, "Department deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting department: " + ex.getMessage() + 
                                                "\n(Ensure no employees or courses are assigned to this department)", 
                                                "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a department to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}
