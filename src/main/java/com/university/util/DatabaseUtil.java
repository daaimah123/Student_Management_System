package com.university.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:sqlite:university.db";

    // TODO: Consider implementing connection pooling for better performance
    // TODO: Add configuration management for database URL and other settings
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // I need foreign key support for SQLite - learned this the hard way
            stmt.execute("PRAGMA foreign_keys = ON;");

            // Departments Table - keeping this simple for now
            String createDepartmentsTable = "CREATE TABLE IF NOT EXISTS departments ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL UNIQUE"
                    + ");";
            stmt.execute(createDepartmentsTable);

            // Students Table - I'm using TEXT for dates to keep things simple
            String createStudentsTable = "CREATE TABLE IF NOT EXISTS students ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "firstName TEXT NOT NULL,"
                    + "lastName TEXT NOT NULL,"
                    + "email TEXT UNIQUE,"
                    + "dateOfBirth TEXT"
                    + ");";
            stmt.execute(createStudentsTable);

            // Employees Table - linking to departments with foreign key
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
            
            // Courses Table - also linking to departments
            String createCoursesTable = "CREATE TABLE IF NOT EXISTS courses ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "courseCode TEXT NOT NULL UNIQUE,"
                    + "courseName TEXT NOT NULL,"
                    + "credits INTEGER,"
                    + "departmentId INTEGER,"
                    + "FOREIGN KEY (departmentId) REFERENCES departments(id) ON DELETE SET NULL"
                    + ");";
            stmt.execute(createCoursesTable);

            // Enrollments Table - the many-to-many relationship between students and courses
            String createEnrollmentsTable = "CREATE TABLE IF NOT EXISTS enrollments ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "studentId INTEGER NOT NULL,"
                    + "courseId INTEGER NOT NULL,"
                    + "enrollmentDate TEXT,"
                    + "FOREIGN KEY (studentId) REFERENCES students(id) ON DELETE CASCADE,"
                    + "FOREIGN KEY (courseId) REFERENCES courses(id) ON DELETE CASCADE,"
                    + "UNIQUE (studentId, courseId)"  // I don't want duplicate enrollments
                    + ");";
            stmt.execute(createEnrollmentsTable);

            // Grades Table - one grade per enrollment
            String createGradesTable = "CREATE TABLE IF NOT EXISTS grades ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "enrollmentId INTEGER NOT NULL UNIQUE," // Each enrollment gets exactly one grade
                    + "gradeValue TEXT," // I'm keeping this flexible - could be A, B, C or 4.0, 3.0
                    + "comments TEXT,"
                    + "FOREIGN KEY (enrollmentId) REFERENCES enrollments(id) ON DELETE CASCADE"
                    + ");";
            stmt.execute(createGradesTable);

            // TODO: Add database indexes for better performance:
            // CREATE INDEX idx_student_email ON students(email);
            // CREATE INDEX idx_employee_department ON employees(departmentId);
            // CREATE INDEX idx_enrollment_student ON enrollments(studentId);
            // CREATE INDEX idx_enrollment_course ON enrollments(courseId);

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            // TODO: Replace printStackTrace with proper logging framework
            e.printStackTrace();
        }
    }
}
