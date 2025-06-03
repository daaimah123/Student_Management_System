package com.university.gui;

import com.university.gui.panels.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("University Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null); // Center the window

        JTabbedPane tabbedPane = new JTabbedPane();

        // Add panels for each module
        tabbedPane.addTab("Students", new StudentPanel());
        tabbedPane.addTab("Departments", new DepartmentPanel());
        tabbedPane.addTab("Employees", new EmployeePanel());
        tabbedPane.addTab("Courses", new CoursePanel());
        tabbedPane.addTab("Enrollments", new EnrollmentPanel());
        tabbedPane.addTab("Grades", new GradePanel());


        add(tabbedPane, BorderLayout.CENTER);
    }
}
