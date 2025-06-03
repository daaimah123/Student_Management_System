package com.university.gui.panels;

import com.university.model.Enrollment;
import com.university.model.Student;
import com.university.model.Course;
import com.university.service.EnrollmentService;
import com.university.service.StudentService;
import com.university.service.CourseService;
import com.university.gui.dialogs.EnrollmentDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class EnrollmentPanel extends JPanel {
    private EnrollmentService enrollmentService;
    private StudentService studentService;
    private CourseService courseService;
    private JTable enrollmentTable;
    private DefaultTableModel tableModel;

    public EnrollmentPanel() {
        enrollmentService = new EnrollmentService();
        studentService = new StudentService();
        courseService = new CourseService();
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Student", "Course", "Enrollment Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
             @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        enrollmentTable = new JTable(tableModel);
        enrollmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(enrollmentTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Enrollment");
        // Edit for enrollment is less common, usually delete and re-add if mistake
        JButton deleteButton = new JButton("Delete Enrollment");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadEnrollments();

        addButton.addActionListener(this::addEnrollmentAction);
        deleteButton.addActionListener(this::deleteEnrollmentAction);
        refreshButton.addActionListener(e -> loadEnrollments());
    }

    private void loadEnrollments() {
        try {
            List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
            tableModel.setRowCount(0);
            for (Enrollment enr : enrollments) {
                String studentName = enr.getStudent() != null ? enr.getStudent().toString() : "N/A (ID: " + enr.getStudentId() + ")";
                String courseName = enr.getCourse() != null ? enr.getCourse().toString() : "N/A (ID: " + enr.getCourseId() + ")";
                tableModel.addRow(new Object[]{
                        enr.getId(),
                        studentName,
                        courseName,
                        enr.getEnrollmentDate()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading enrollments: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEnrollmentAction(ActionEvent e) {
        EnrollmentDialog dialog = new EnrollmentDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                                     "Add Enrollment", null, enrollmentService, studentService, courseService);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            loadEnrollments();
        }
    }

    private void deleteEnrollmentAction(ActionEvent e) {
        int selectedRow = enrollmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            int enrollmentId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this enrollment (ID: " + enrollmentId + ")?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    enrollmentService.deleteEnrollment(enrollmentId);
                    loadEnrollments();
                    JOptionPane.showMessageDialog(this, "Enrollment deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting enrollment: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an enrollment to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}
