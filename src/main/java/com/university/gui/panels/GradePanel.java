package com.university.gui.panels;

import com.university.model.Grade;
import com.university.model.Enrollment;
import com.university.service.GradeService;
import com.university.service.EnrollmentService;
import com.university.gui.dialogs.GradeDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class GradePanel extends JPanel {
    private GradeService gradeService;
    private EnrollmentService enrollmentService; // To get enrollment details
    private JTable gradeTable;
    private DefaultTableModel tableModel;

    public GradePanel() {
        gradeService = new GradeService();
        enrollmentService = new EnrollmentService();
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Enrollment (Student - Course)", "Grade Value", "Comments"};
        tableModel = new DefaultTableModel(columnNames, 0) {
             @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        gradeTable = new JTable(tableModel);
        gradeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(gradeTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add/Edit Grade"); // Combined as grade is unique per enrollment
        JButton deleteButton = new JButton("Delete Grade");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadGrades();

        addButton.addActionListener(this::addOrEditGradeAction);
        deleteButton.addActionListener(this::deleteGradeAction);
        refreshButton.addActionListener(e -> loadGrades());
    }

    private void loadGrades() {
        try {
            List<Grade> grades = gradeService.getAllGrades();
            tableModel.setRowCount(0);
            for (Grade grade : grades) {
                String enrollmentInfo = "N/A";
                if (grade.getEnrollment() != null) {
                     enrollmentInfo = (grade.getEnrollment().getStudent() != null ? grade.getEnrollment().getStudent().toString() : "Unknown Student") + 
                                     " - " + 
                                     (grade.getEnrollment().getCourse() != null ? grade.getEnrollment().getCourse().toString() : "Unknown Course");
                } else if (grade.getEnrollmentId() > 0) {
                    enrollmentInfo = "Enrollment ID: " + grade.getEnrollmentId();
                }

                tableModel.addRow(new Object[]{
                        grade.getId(),
                        enrollmentInfo,
                        grade.getGradeValue(),
                        grade.getComments()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading grades: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addOrEditGradeAction(ActionEvent e) {
        // This dialog will handle both adding a new grade (by selecting an ungraded enrollment)
        // or editing an existing grade (by selecting a graded enrollment or the grade itself).
        GradeDialog dialog = new GradeDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                             "Add/Edit Grade", gradeService, enrollmentService);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            loadGrades();
        }
    }

    private void deleteGradeAction(ActionEvent e) {
        int selectedRow = gradeTable.getSelectedRow();
        if (selectedRow >= 0) {
            int gradeId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this grade (ID: " + gradeId + ")?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    gradeService.deleteGrade(gradeId);
                    loadGrades();
                    JOptionPane.showMessageDialog(this, "Grade deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting grade: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a grade to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}
