package com.university.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:sqlite:university.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Enable foreign key support for SQLite
            stmt.execute("PRAGMA foreign_keys = ON;");

            // Departments Table
            String createDepartmentsTable = "CREATE TABLE IF NOT EXISTS departments ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL UNIQUE"
                    + ");";
            stmt.execute(createDepartmentsTable);

            // Students Table
            String createStudentsTable = "CREATE TABLE IF NOT EXISTS students ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "firstName TEXT NOT NULL,"
                    + "lastName TEXT NOT NULL,"
                    + "email TEXT UNIQUE,"
                    + "dateOfBirth TEXT"
                    + ");";
            stmt.execute(createStudentsTable);

            // Employees Table
            String createEmployeesTable = "CREATE TABLE IF NOT EXISTS employees ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "firstName TEXT NOT NULL,"
                    + "lastName TEXT NOT NULL,"
                    + "email TEXT UNIQUE,"
                    + "dateOfBirth TEXT,"
                    + "position TEXT,"
                    + "departmentId INTEGER,"
                    + "FOREIGN KEY (departmentId) REFERENCES departments(id) ON DELETE SET NULL"
                    + ");";
            stmt.execute(createEmployeesTable);
            
            // Courses Table
            String createCoursesTable = "CREATE TABLE IF NOT EXISTS courses ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "courseCode TEXT NOT NULL UNIQUE,"
                    + "courseName TEXT NOT NULL,"
                    + "credits INTEGER,"
                    + "departmentId INTEGER,"
                    + "FOREIGN KEY (departmentId) REFERENCES departments(id) ON DELETE SET NULL"
                    + ");";
            stmt.execute(createCoursesTable);

            // Enrollments Table
            String createEnrollmentsTable = "CREATE TABLE IF NOT EXISTS enrollments ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "studentId INTEGER NOT NULL,"
                    + "courseId INTEGER NOT NULL,"
                    + "enrollmentDate TEXT,"
                    + "FOREIGN KEY (studentId) REFERENCES students(id) ON DELETE CASCADE,"
                    + "FOREIGN KEY (courseId) REFERENCES courses(id) ON DELETE CASCADE,"
                    + "UNIQUE (studentId, courseId)"
                    + ");";
            stmt.execute(createEnrollmentsTable);

            // Grades Table
            String createGradesTable = "CREATE TABLE IF NOT EXISTS grades ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "enrollmentId INTEGER NOT NULL UNIQUE," // Each enrollment gets one grade
                    + "gradeValue TEXT," // e.g., A, B, C or 4.0, 3.0
                    + "comments TEXT,"
                    + "FOREIGN KEY (enrollmentId) REFERENCES enrollments(id) ON DELETE CASCADE"
                    + ");";
            stmt.execute(createGradesTable);

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
