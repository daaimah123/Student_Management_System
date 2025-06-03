package com.university.gui.dialogs;

import com.university.model.Department;
import com.university.service.DepartmentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class DepartmentDialog extends JDialog {
    private JTextField nameField;
    private DepartmentService departmentService;
    private Department currentDepartment;
    private boolean saved = false;

    public DepartmentDialog(Frame owner, String title, Department department, DepartmentService service) {
        super(owner, title, true);
        this.currentDepartment = department;
        this.departmentService = service;

        setLayout(new BorderLayout());
        setSize(350, 150);
        setLocationRelativeTo(owner);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Department Name:"));
        nameField = new JTextField(20);
        formPanel.add(nameField);

        if (currentDepartment != null) {
            nameField.setText(currentDepartment.getName());
        }
        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(this::saveDepartmentAction);
        cancelButton.addActionListener(e -> dispose());
    }

    private void saveDepartmentAction(ActionEvent e) {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Department name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Department deptToSave;
        if (currentDepartment == null) {
            deptToSave = new Department(0, name);
        } else {
            deptToSave = currentDepartment;
            deptToSave.setName(name);
        }

        try {
            if (currentDepartment == null) {
                departmentService.addDepartment(deptToSave);
                 JOptionPane.showMessageDialog(this, "Department added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                departmentService.updateDepartment(deptToSave);
                JOptionPane.showMessageDialog(this, "Department updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            saved = true;
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving department: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
