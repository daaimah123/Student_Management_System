package com.university.gui.dialogs;

import com.university.model.Employee;
import com.university.model.Department;
import com.university.service.EmployeeService;
import com.university.service.DepartmentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class EmployeeDialog extends JDialog {
    private JTextField firstNameField, lastNameField, emailField, dobField, positionField;
    private JComboBox<Department> departmentComboBox;
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private Employee currentEmployee;
    private boolean saved = false;

    public EmployeeDialog(Frame owner, String title, Employee employee, EmployeeService empService, DepartmentService deptService) {
        super(owner, title, true);
        this.currentEmployee = employee;
        this.employeeService = empService;
        this.departmentService = deptService;

        setLayout(new BorderLayout());
        setSize(450, 350);
        setLocationRelativeTo(owner);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField(20);
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(20);
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        formPanel.add(emailField);

        formPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        dobField = new JTextField(20);
        formPanel.add(dobField);

        formPanel.add(new JLabel("Position:"));
        positionField = new JTextField(20);
        formPanel.add(positionField);

        formPanel.add(new JLabel("Department:"));
        departmentComboBox = new JComboBox<>();
        loadDepartmentsIntoComboBox();
        formPanel.add(departmentComboBox);


        if (currentEmployee != null) {
            firstNameField.setText(currentEmployee.getFirstName());
            lastNameField.setText(currentEmployee.getLastName());
            emailField.setText(currentEmployee.getEmail());
            dobField.setText(currentEmployee.getDateOfBirth());
            positionField.setText(currentEmployee.getPosition());
            if (currentEmployee.getDepartment() != null) {
                departmentComboBox.setSelectedItem(currentEmployee.getDepartment());
            } else if (currentEmployee.getDepartmentId() > 0) {
                // Attempt to find and select department by ID if object is not set
                try {
                    Department dept = departmentService.getDepartment(currentEmployee.getDepartmentId());
                    if (dept != null) departmentComboBox.setSelectedItem(dept);
                } catch (SQLException ex) { /* ignore, might not find */ }
            }
        }
        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(this::saveEmployeeAction);
        cancelButton.addActionListener(e -> dispose());
    }

    private void loadDepartmentsIntoComboBox() {
        try {
            List<Department> departments = departmentService.getAllDepartments();
            departmentComboBox.addItem(null); // Option for no department
            for (Department dept : departments) {
                departmentComboBox.addItem(dept);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading departments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveEmployeeAction(ActionEvent e) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String dob = dobField.getText().trim();
        String position = positionField.getText().trim();
        Department selectedDepartment = (Department) departmentComboBox.getSelectedItem();

        if (firstName.isEmpty() || lastName.isEmpty() || position.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First name, last name, and position cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employee empToSave;
        if (currentEmployee == null) {
            empToSave = new Employee(0, firstName, lastName, email, dob, position, selectedDepartment);
        } else {
            empToSave = currentEmployee;
            empToSave.setFirstName(firstName);
            empToSave.setLastName(lastName);
            empToSave.setEmail(email);
            empToSave.setDateOfBirth(dob);
            empToSave.setPosition(position);
            empToSave.setDepartment(selectedDepartment);
        }

        try {
            if (currentEmployee == null) {
                employeeService.addEmployee(empToSave);
                JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                employeeService.updateEmployee(empToSave);
                JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            saved = true;
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving employee: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Validation Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
