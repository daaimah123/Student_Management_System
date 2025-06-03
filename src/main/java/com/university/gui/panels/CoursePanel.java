package com.university.gui.panels;

import com.university.model.Course;
import com.university.model.Department;
import com.university.service.CourseService;
import com.university.service.DepartmentService;
import com.university.gui.dialogs.CourseDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class CoursePanel extends JPanel {
    private CourseService courseService;
    private DepartmentService departmentService;
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public CoursePanel() {
        courseService = new CourseService();
        departmentService = new DepartmentService();
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Code", "Name", "Credits", "Department"};
        tableModel = new DefaultTableModel(columnNames, 0) {
             @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        courseTable = new JTable(tableModel);
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Course");
        JButton editButton = new JButton("Edit Course");
        JButton deleteButton = new JButton("Delete Course");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadCourses();

        addButton.addActionListener(this::addCourseAction);
        editButton.addActionListener(this::editCourseAction);
        deleteButton.addActionListener(this::deleteCourseAction);
        refreshButton.addActionListener(e -> loadCourses());
    }

    private void loadCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();
            tableModel.setRowCount(0);
            for (Course course : courses) {
                String departmentName = course.getDepartment() != null ? course.getDepartment().getName() : "N/A";
                tableModel.addRow(new Object[]{
                        course.getId(),
                        course.getCourseCode(),
                        course.getCourseName(),
                        course.getCredits(),
                        departmentName
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCourseAction(ActionEvent e) {
        CourseDialog dialog = new CourseDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Course", null, courseService, departmentService);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            loadCourses();
        }
    }

    private void editCourseAction(ActionEvent e) {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow >= 0) {
            int courseId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                Course courseToEdit = courseService.getCourse(courseId);
                if (courseToEdit != null) {
                    CourseDialog dialog = new CourseDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Course", courseToEdit, courseService, departmentService);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        loadCourses();
                    }
                } else {
                     JOptionPane.showMessageDialog(this, "Course not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error fetching course: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a course to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteCourseAction(ActionEvent e) {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow >= 0) {
            int courseId = (int) tableModel.getValueAt(selectedRow, 0);
            String courseName = (String) tableModel.getValueAt(selectedRow, 2);
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete course: " + courseName + " (ID: " + courseId + ")?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    courseService.deleteCourse(courseId);
                    loadCourses();
                    JOptionPane.showMessageDialog(this, "Course deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting course: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a course to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}
