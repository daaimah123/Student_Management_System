# University Management System

## Overview

The University Management System is a comprehensive desktop application developed in Java using the Swing toolkit for its Graphical User Interface (GUI). It provides administrators with tools to manage various aspects of a university, including student records, employee information, course details, departmental data, course enrollments, and student grades. The system uses an embedded SQLite database for persistent data storage.

## Features

*   **Student Management**: Add, view, update, and delete student records.
*   **Employee Management**: Add, view, update, and delete employee records (faculty and staff).
*   **Course Management**: Define and manage academic courses.
*   **Department Management**: Manage university departments.
*   **Course Enrollment**: Enroll students in available courses.
*   **Grade Management**: Assign and track grades for students in enrolled courses.
*   **User-Friendly GUI**: Intuitive tabbed interface for easy navigation between modules.
*   **Persistent Data Storage**: Utilizes an embedded SQLite database (`university.db`) created in the project root.
*   **Automatic Database Setup**: Tables are automatically created if they don't exist on first run.

## Architecture

The system is built upon a layered architecture:
1.  **Presentation Layer (GUI)**: Java Swing components for user interaction.
2.  **Service Layer**: Contains business logic and orchestrates operations.
3.  **Data Access Layer (DAO)**: Manages data persistence using SQLite.
4.  **Domain Model Layer**: Represents the core entities of the system.

## Technologies Used

*   **Java**: Core programming language (JDK 11 or higher recommended).
*   **Java Swing**: For the graphical user interface.
*   **SQLite**: Embedded relational database.
*   **JDBC**: For database connectivity.
*   **Maven**: For project build and dependency management.

## Setup and Running Locally (e.g., in VS Code)

### Prerequisites

1.  **Java Development Kit (JDK)**: Version 11 or newer. Make sure `JAVA_HOME` is set and `java` is in your PATH.
2.  **Apache Maven**: For building the project. Make sure `mvn` is in your PATH.
3.  **VS Code**: With the "Extension Pack for Java" installed.

### Steps to Run

1.  **Clone the Repository (or download the source code)**:
    ```bash
    git clone <repository-url>
    cd UniversityManagementSystem
    ```
(If you downloaded a ZIP, extract it and navigate to the project root directory).

2. **Open in VS Code**:

1. Open VS Code.
2. Go to `File > Open Folder...` and select the `UniversityManagementSystem` project root directory.
3. VS Code should automatically recognize it as a Maven project.

3. **Build the Project**:

1. Open the terminal in VS Code (`Terminal > New Terminal`).
2. Run the Maven build command: `mvn clean install`

This will compile the code and download necessary dependencies (like the SQLite JDBC driver).

4. **Run the Application**:
1. Locate the `UniversityManagementApp.java` file in `src/main/java/com/university/main/`.
2. Right-click on the file in the VS Code explorer or directly in the editor.
3. Select "Run Java".
4. Alternatively, VS Code might show a "Run" CodeLens above the `main` method.


5. **Database File**:
Upon first run, an SQLite database file named `university.db` will be created in the root directory of the project. All application data will be stored here.


## Project Structure

The project follows a standard Maven directory layout:

```
UniversityManagementSystem/
├── pom.xml                   # Maven Project Object Model
├── university.db             # SQLite database file (created on run)
└── src/
└── main/
└── java/
└── com/
└── university/
├── main/         # Main application class
├── model/        # Domain objects (Student, Employee, etc.)
├── dao/          # Data Access Object interfaces
│   └── impl/     # DAO implementations
├── service/      # Business logic services
├── gui/          # Main GUI Frame
│   ├── panels/   # JPanels for each module
│   └── dialogs/  # JDialogs for forms
└── util/         # Utility classes (DatabaseUtil, etc.)
```

## Error Handling

- The application includes basic error handling for database operations and input validation.
- Error messages will be displayed via `JOptionPane` dialogs in the GUI.
- Console logs might provide additional details for developers, especially for database initialization errors.