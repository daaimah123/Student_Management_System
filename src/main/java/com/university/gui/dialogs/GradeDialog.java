package com.university.gui.dialogs;

import com.university.model.Grade;
import com.university.model.Enrollment;
import com.university.service.GradeService;
import com.university.service.EnrollmentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class GradeDialog extends JDialog {
    private JComboBox<Enrollment> enrollmentComboBox;
    private JTextField gradeValueField, commentsField;

    private GradeService gradeService;
    private EnrollmentService enrollmentService;
    private Grade currentGrade; // If editing an existing grade
    private boolean saved = false;

    public GradeDialog(Frame owner, String title, GradeService gService, EnrollmentService eService) {
        super(owner, title, true);
        this.gradeService = gService;
        this.enrollmentService = eService;

        setLayout(new BorderLayout());
        setSize(500, 250);
        setLocationRelativeTo(owner);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Enrollment (Student - Course):"));
        enrollmentComboBox = new JComboBox<>();
        loadEnrollmentsIntoComboBox(); // Load all, or just ungraded ones
        formPanel.add(enrollmentComboBox);

        formPanel.add(new JLabel("Grade Value:"));
        gradeValueField = new JTextField(10);
        formPanel.add(gradeValueField);

        formPanel.add(new JLabel("Comments:"));
        commentsField = new JTextField(20);
        formPanel.add(commentsField);
        
        enrollmentComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Enrollment selectedEnrollment = (Enrollment) enrollmentComboBox.getSelectedItem();
                if (selectedEnrollment != null) {
                    populateFieldsForEnrollment(selectedEnrollment);
                } else {
                    clearGradeFields();
                }
            }
        });


        // If a grade is passed for editing, select its enrollment and populate fields
        // This part is tricky if the dialog is for both add/edit.
        // For simplicity, this dialog will primarily be for adding a grade to an enrollment,
        // or editing if an enrollment already has a grade.

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save Grade");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(this::saveGradeAction);
        cancelButton.addActionListener(e -> dispose());
        
        // Initial population if an enrollment is pre-selected
        if (enrollmentComboBox.getItemCount() > 0) {
            populateFieldsForEnrollment((Enrollment) enrollmentComboBox.getSelectedItem());
        }
    }
    
    private void populateFieldsForEnrollment(Enrollment selectedEnrollment) {
        if (selectedEnrollment == null) {
            clearGradeFields();
            return;
        }
        try {
            currentGrade = gradeService.getGradeByEnrollment(selectedEnrollment.getId());
            if (currentGrade != null) {
                gradeValueField.setText(currentGrade.getGradeValue());
                commentsField.setText(currentGrade.getComments());
            } else {
                clearGradeFields(); // No grade exists for this enrollment yet
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching existing grade: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            clearGradeFields();
        }
    }

    private void clearGradeFields() {
        gradeValueField.setText("");
        commentsField.setText("");
        currentGrade = null;
    }


    private void loadEnrollmentsIntoComboBox() {
        try {
            // Optionally, filter to show only ungraded enrollments or all
            List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
            // For simplicity, load all. Could be refined.
            for (Enrollment enr : enrollments) {
                enrollmentComboBox.addItem(enr);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading enrollments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveGradeAction(ActionEvent e) {
        Enrollment selectedEnrollment = (Enrollment) enrollmentComboBox.getSelectedItem();
        String gradeValue = gradeValueField.getText().trim();
        String comments = commentsField.getText().trim();

        if (selectedEnrollment == null) {
            JOptionPane.showMessageDialog(this, "An enrollment must be selected.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (gradeValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Grade value cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (currentGrade == null) { // Adding new grade for this enrollment
                gradeService.addGrade(selectedEnrollment, gradeValue, comments);
                JOptionPane.showMessageDialog(this, "Grade added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else { // Editing existing grade
                currentGrade.setGradeValue(gradeValue);
                currentGrade.setComments(comments);
                // currentGrade.setEnrollment(selectedEnrollment); // Should already be set
                gradeService.updateGrade(currentGrade);
                JOptionPane.showMessageDialog(this, "Grade updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            saved = true;
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving grade: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Validation Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
