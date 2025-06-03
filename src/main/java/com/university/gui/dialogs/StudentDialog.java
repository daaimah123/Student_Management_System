package com.university.gui.dialogs;

import com.university.model.Student;
import com.university.service.StudentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class StudentDialog extends JDialog {
    private JTextField firstNameField, lastNameField, emailField, dobField;
    private StudentService studentService;
    private Student currentStudent; // For editing
    private boolean saved = false;

    public StudentDialog(Frame owner, String title, Student student, StudentService service) {
        super(owner, title, true); // true for modal
        this.currentStudent = student;
        this.studentService = service;

        setLayout(new BorderLayout());
        setSize(400, 250);
        setLocationRelativeTo(owner);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // rows, cols, hgap, vgap
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

        if (currentStudent != null) {
            firstNameField.setText(currentStudent.getFirstName());
            lastNameField.setText(currentStudent.getLastName());
            emailField.setText(currentStudent.getEmail());
            dobField.setText(currentStudent.getDateOfBirth());
        }

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(this::saveStudentAction);
        cancelButton.addActionListener(e -> dispose());
    }

    private void saveStudentAction(ActionEvent e) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String dob = dobField.getText().trim(); // Add date validation if needed

        if (firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First name and last name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Basic email validation (can be more robust)
        if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        Student studentToSave;
        if (currentStudent == null) { // Adding new student
            studentToSave = new Student(0, firstName, lastName, email, dob);
        } else { // Editing existing student
            studentToSave = currentStudent;
            studentToSave.setFirstName(firstName);
            studentToSave.setLastName(lastName);
            studentToSave.setEmail(email);
            studentToSave.setDateOfBirth(dob);
        }

        try {
            if (currentStudent == null) {
                studentService.addStudent(studentToSave);
                JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                studentService.updateStudent(studentToSave);
                JOptionPane.showMessageDialog(this, "Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            saved = true;
            dispose(); // Close dialog
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving student: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Validation Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
