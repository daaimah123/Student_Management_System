package com.university.gui.dialogs;

import com.university.model.Course;
import com.university.model.Department;
import com.university.service.CourseService;
import com.university.service.DepartmentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDialog extends JDialog {
    private JTextField courseCodeField, courseNameField, courseCreditField;
    private JComboBox<Department> courseDeptField;
    private CourseService CourseService;
    private Course currentCourse; // For editing existing courses
    private DepartmentService departmentService = new DepartmentService();
    private boolean saved = false;

    public CourseDialog(Frame owner, String title, Course course, CourseService service) throws SQLException{
        super(owner, title, true); // Making it modal so users can't click away
        this.currentCourse = course;
        this.CourseService = service;

        setLayout(new BorderLayout());
        setSize(400, 250); // I sized this to fit the form nicely
        setLocationRelativeTo(owner);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // The spacing makes it look cleaner
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Course Code:"));
        courseCodeField = new JTextField(20);
        formPanel.add(courseCodeField);

        formPanel.add(new JLabel("Course Name:"));
        courseNameField = new JTextField(20);
        formPanel.add(courseNameField);

        formPanel.add(new JLabel("Course Credits:"));
        courseCreditField = new JTextField(20);
        formPanel.add(courseCreditField);

        formPanel.add(new JLabel("Department"));
        List<Department> departmentWithNone = new ArrayList<Department>();
        departmentWithNone.add(null);
        departmentWithNone.addAll(departmentService.getAllDepartments());
        courseDeptField = new JComboBox<>(departmentWithNone.toArray(new Department[0]));
        courseDeptField.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
                if (value == null) {
                    value = "--No Department--";  // shown in dropdown for 'null'
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        formPanel.add(courseDeptField);

        // If we're editing, populate the fields with existing data
        if (currentCourse != null) {
            courseCodeField.setText(currentCourse.getCourseCode());
            courseNameField.setText(currentCourse.getCourseName());
            courseCreditField.setText(String.valueOf(currentCourse.getCredits()));
            courseDeptField.setSelectedItem(departmentService.getDepartment(currentCourse.getDepartmentId()));
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
        
        // TODO: Add Enter key support for save action
        // TODO: Add Escape key support for cancel action
    }

    private void saveCourseAction(ActionEvent e) {
        String courseCode = courseCodeField.getText().trim().toUpperCase();
        int courseCredits = Integer.parseInt(courseCreditField.getText().trim());
        String courseName = courseNameField.getText().trim();
        Department department = (Department) courseDeptField.getSelectedItem();

        if (courseCode == null || courseCode.trim().isEmpty() ||
            courseName == null || courseName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Course code and name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (courseCredits <= 0) {
            JOptionPane.showMessageDialog(this, "Credits must be positive.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!(courseCredits >= 1 && courseCredits <= 4)) {
            JOptionPane.showMessageDialog(this, "Course Credits should be in the range 1-4.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Checks for courseCode matching specific type Ex: CS101
        if (!courseCode.isEmpty() && !courseCode.matches("^[A-Z]+[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Course Code is in wrong format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Course courseToSave;
        if (currentCourse == null) { // Adding new course
            courseToSave = new Course(0, courseCode, courseName, courseCredits, department);
        } else { // Editing existing course
            courseToSave = currentCourse;
            courseToSave.setCourseCode(courseCode);
            courseToSave.setCourseName(courseName);
            courseToSave.setCredits(courseCredits);
            courseToSave.setDepartment(department);
        }

        try {
            if (currentCourse == null) {
                CourseService.addCourse(courseToSave);
                JOptionPane.showMessageDialog(this, "Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                CourseService.updateCourse(courseToSave);
                JOptionPane.showMessageDialog(this, "Course updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            saved = true;
            dispose(); // Close the dialog
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving course: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Validation Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved; // The parent panel uses this to know if it should refresh
    }
}

