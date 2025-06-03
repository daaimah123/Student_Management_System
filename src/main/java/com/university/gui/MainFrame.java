package com.university.gui;

import com.university.gui.panels.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("University Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // I picked this size after some trial and error
        setLocationRelativeTo(null); // This centers the window nicely

        JTabbedPane tabbedPane = new JTabbedPane();

        // I'm adding panels for each module - the order matters for user experience
        tabbedPane.addTab("Students", new StudentPanel());
        tabbedPane.addTab("Departments", new DepartmentPanel()); // Departments first since other things depend on them
        tabbedPane.addTab("Employees", new EmployeePanel());
        tabbedPane.addTab("Courses", new CoursePanel());
        tabbedPane.addTab("Enrollments", new EnrollmentPanel());
        tabbedPane.addTab("Grades", new GradePanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
