package com.university.gui.panels;

import com.university.model.Student;
import com.university.service.StudentService;
import com.university.gui.dialogs.StudentDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class StudentPanel extends JPanel {
    private StudentService studentService;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public StudentPanel() {
        studentService = new StudentService();
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"ID", "First Name", "Last Name", "Email", "Date of Birth"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Student");
        JButton editButton = new JButton("Edit Student");
        JButton deleteButton = new JButton("Delete Student");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load initial data
        loadStudents();

        // Action Listeners
        addButton.addActionListener(this::addStudentAction);
        editButton.addActionListener(this::editStudentAction);
        deleteButton.addActionListener(this::deleteStudentAction);
        refreshButton.addActionListener(e -> loadStudents());
    }

    private void loadStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            tableModel.setRowCount(0); // Clear existing data
            for (Student student : students) {
                tableModel.addRow(new Object[]{
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getDateOfBirth()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addStudentAction(ActionEvent e) {
        StudentDialog dialog = new StudentDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Student", null, studentService);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            loadStudents(); // Refresh table
        }
    }

    private void editStudentAction(ActionEvent e) {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                Student studentToEdit = studentService.getStudent(studentId);
                if (studentToEdit != null) {
                    StudentDialog dialog = new StudentDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Student", studentToEdit, studentService);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        loadStudents(); // Refresh table
                    }
                } else {
                     JOptionPane.showMessageDialog(this, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error fetching student for edit: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteStudentAction(ActionEvent e) {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            String studentName = tableModel.getValueAt(selectedRow, 1) + " " + tableModel.getValueAt(selectedRow, 2);
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete student: " + studentName + " (ID: " + studentId + ")?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    studentService.deleteStudent(studentId);
                    loadStudents(); // Refresh table
                    JOptionPane.showMessageDialog(this, "Student deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting student: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}
