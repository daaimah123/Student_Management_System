package com.university.gui.dialogs;

import com.university.model.Course;
import com.university.model.Department;
import com.university.service.CourseService;
import com.university.service.DepartmentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class CourseDialog extends JDialog {
    private JTextField codeField, nameField, creditsField;
    private JComboBox<Department> departmentComboBox;
    private CourseService courseService;
    private DepartmentService departmentService;
    private Course currentCourse;
    private boolean saved = false;

    public CourseDialog(Frame owner, String title, Course course, CourseService cService, DepartmentService dService) {
        super(owner, title, true);
        this.currentCourse = course;
        this.courseService = cService;
        this.departmentService = dService;

        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(owner);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Course Code:"));
        codeField = new JTextField(20);
        formPanel.add(codeField);

        formPanel.add(new JLabel("Course Name:"));
        nameField = new JTextField(20);
        formPanel.add(nameField);

        formPanel.add(new JLabel("Credits:"));
        creditsField = new JTextField(5);
        formPanel.add(creditsField);

        formPanel.add(new JLabel("Department:"));
        departmentComboBox = new JComboBox<>();
        loadDepartmentsIntoComboBox();
        formPanel.add(departmentComboBox);

        if (currentCourse != null) {
            codeField.setText(currentCourse.getCourseCode());
            nameField.setText(currentCourse.getCourseName());
            creditsField.setText(String.valueOf(currentCourse.getCredits()));
            if (currentCourse.getDepartment() != null) {
                departmentComboBox.setSelectedItem(currentCourse.getDepartment());
            } else if (currentCourse.getDepartmentId() > 0) {
                 try {
                    Department dept = departmentService.getDepartment(currentCourse.getDepartmentId());
                    if (dept != null) departmentComboBox.setSelectedItem(dept);
                } catch (SQLException ex) { /* ignore */ }
            }
        }
        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(this::saveCourseAction);
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


    private void saveCourseAction(ActionEvent e) {
        String code = codeField.getText().trim();
        String name = nameField.getText().trim();
        String creditsStr = creditsField.getText().trim();
        Department selectedDepartment = (Department) departmentComboBox.getSelectedItem();

        if (code.isEmpty() || name.isEmpty() || creditsStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Code, name, and credits cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int credits;
        try {
            credits = Integer.parseInt(creditsStr);
            if (credits <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Credits must be a positive integer.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Course courseToSave;
        if (currentCourse == null) {
            courseToSave = new Course(0, code, name, credits, selectedDepartment);
        } else {
            courseToSave = currentCourse;
            courseToSave.setCourseCode(code);
            courseToSave.setCourseName(name);
            courseToSave.setCredits(credits);
            courseToSave.setDepartment(selectedDepartment);
        }

        try {
            if (currentCourse == null) {
                courseService.addCourse(courseToSave);
                JOptionPane.showMessageDialog(this, "Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                courseService.updateCourse(courseToSave);
                JOptionPane.showMessageDialog(this, "Course updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            saved = true;
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving course: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Validation Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
