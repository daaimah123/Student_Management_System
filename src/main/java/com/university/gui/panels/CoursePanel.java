package com.university.gui.panels;

import com.university.model.Course;
import com.university.service.CourseService;
import com.university.gui.dialogs.CourseDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class CoursePanel extends JPanel {
    private CourseService courseService;
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public CoursePanel() {
        courseService = new CourseService();
        setLayout(new BorderLayout());
        
        String[] columnNames = {"Id", "Course Code", "Course Name", "Course Credits", "Department"};
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

        // Load initial data when the panel is created
        loadCourses();

        addButton.addActionListener(this::addCourseAction);
        editButton.addActionListener(this::editCourseAction);
        deleteButton.addActionListener(this::deleteCourseAction);
        refreshButton.addActionListener(e -> loadCourses());
    }

    private void loadCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();
            tableModel.setRowCount(0); // Clear existing data first
            for (Course course : courses) {
                tableModel.addRow(new Object[]{
                        course.getId(),
                        course.getCourseCode(),
                        course.getCourseName(),
                        course.getCredits(),
                        course.getDepartment() == null ? null : course.getDepartment().getName()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addCourseAction(ActionEvent e){
        try{
            CourseDialog dialog = new CourseDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Course", null, courseService);
            dialog.setVisible(true);
            if (dialog.isSaved()) {
                loadCourses(); // Refresh the table if something was saved
            }
        } catch(SQLException ex){

        }
       
    }

    private void editCourseAction(ActionEvent e) {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow >= 0) {
            int courseId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                Course courseToEdit = courseService.getCourse(courseId);
                if (courseToEdit != null) {
                    CourseDialog dialog = new CourseDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Course", courseToEdit, courseService);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        loadCourses(); // Refresh the table
                    }
                } else {
                     JOptionPane.showMessageDialog(this, "Course not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error fetching course for edit: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a course to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteCourseAction(ActionEvent e) {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow >= 0) {
            int courseId = (int) tableModel.getValueAt(selectedRow, 0);
            
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete course: " + " (ID: " + courseId + ")?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    courseService.deleteCourse(courseId);
                    loadCourses(); // Refresh the table
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
