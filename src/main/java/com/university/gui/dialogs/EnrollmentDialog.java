package com.university.gui.dialogs;

import com.university.model.Enrollment;
import com.university.model.Student;
import com.university.model.Course;
import com.university.service.EnrollmentService;
import com.university.service.StudentService;
import com.university.service.CourseService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EnrollmentDialog extends JDialog {
    private JComboBox<Student> studentComboBox;
    private JComboBox<Course> courseComboBox;
    private JTextField dateField; // For enrollment date, can be auto-filled

    private EnrollmentService enrollmentService;
    private StudentService studentService;
    private CourseService courseService;
    private Enrollment currentEnrollment; // For editing, though less common
    private boolean saved = false;

    public EnrollmentDialog(Frame owner, String title, Enrollment enrollment, 
                            EnrollmentService enrService, StudentService stuService, CourseService couService) {
        super(owner, title, true);
        this.currentEnrollment = enrollment;
        this.enrollmentService = enrService;
        this.studentService = stuService;
        this.courseService = couService;

        setLayout(new BorderLayout());
        setSize(450, 250);
        setLocationRelativeTo(owner);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Student:"));
        studentComboBox = new JComboBox<>();
        loadStudentsIntoComboBox();
        formPanel.add(studentComboBox);

        formPanel.add(new JLabel("Course:"));
        courseComboBox = new JComboBox<>();
        loadCoursesIntoComboBox();
        formPanel.add(courseComboBox);

        formPanel.add(new JLabel("Enrollment Date (YYYY-MM-DD):"));
        dateField = new JTextField(LocalDate.now().format(DateTimeFormatter.ISO_DATE)); // Default to today
        formPanel.add(dateField);

        if (currentEnrollment != null) {
            studentComboBox.setSelectedItem(currentEnrollment.getStudent());
            courseComboBox.setSelectedItem(currentEnrollment.getCourse());
            dateField.setText(currentEnrollment.getEnrollmentDate());
            // Disable student/course combo if editing, as these define the enrollment
            studentComboBox.setEnabled(false); 
            courseComboBox.setEnabled(false);
        }
        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(this::saveEnrollmentAction);
        cancelButton.addActionListener(e -> dispose());
    }

    private void loadStudentsIntoComboBox() {
        try {
            List<Student> students = studentService.getAllStudents();
            for (Student s : students) {
                studentComboBox.addItem(s);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCoursesIntoComboBox() {
        try {
            List<Course> courses = courseService.getAllCourses();
            for (Course c : courses) {
                courseComboBox.addItem(c);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveEnrollmentAction(ActionEvent e) {
        Student selectedStudent = (Student) studentComboBox.getSelectedItem();
        Course selectedCourse = (Course) courseComboBox.getSelectedItem();
        String enrollmentDate = dateField.getText().trim();

        if (selectedStudent == null || selectedCourse == null) {
            JOptionPane.showMessageDialog(this, "Student and Course must be selected.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (enrollmentDate.isEmpty()) { // Basic date validation
            JOptionPane.showMessageDialog(this, "Enrollment date cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            LocalDate.parse(enrollmentDate, DateTimeFormatter.ISO_DATE); // Validate date format
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        try {
            if (currentEnrollment == null) { // Adding new
                // Service layer handles ID and actual creation
                enrollmentService.addEnrollment(selectedStudent, selectedCourse); 
                JOptionPane.showMessageDialog(this, "Enrollment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else { // Editing existing (mainly date)
                currentEnrollment.setEnrollmentDate(enrollmentDate);
                enrollmentService.updateEnrollment(currentEnrollment);
                JOptionPane.showMessageDialog(this, "Enrollment updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            saved = true;
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving enrollment: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Validation Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
