# 👩🏽‍🎓Student Management System👩🏽‍🎓
The Student Management System is a desktop application developed using Java Swing that provides administrators with a user-friendly interface to manage student records, handle course enrollments, and assign grades. The application features a tabbed interface with four main modules: Student Management, Course Management, Course Enrollment, and Grade Management.

## Table of Contents
- [Features](#features)
- [System Requirements](#system-requirements)
- [Installation and Setup](#installation-and-setup)
- [User Guide](#user-guide)
- [Technical Documentation](#technical-documentation)
- [Design Choices](#design-choices)
- [Error Handling](#error-handling)
- [Troubleshooting Guide](#troubleshooting-guide)
- [Developer Guide](#developer-guide)
- [Refactoring for Production: Beyond the Assignment](#refactoring-for-production-beyond-the-assignment)

## Features

#### Student Management
- **Add New Students**: Create new student records with ID, name, email, and major
- **Update Student Information**: Modify existing student details
- **View Student Details**: Display all students in a sortable table
- **Delete Students**: Remove student records with confirmation
- **Search Functionality**: Filter students by ID, name, email, or major
- **Input Validation**: Comprehensive validation for all fields
- **Duplicate Prevention**: Prevents duplicate student IDs

#### Course Management
- **Add New Courses**: Create course records with ID, name, credits, and enrollment limits
- **Update Course Information**: Modify existing course details
- **View Course Details**: Display all courses in a sortable table
- **Delete Courses**: Remove course records with confirmation
- **Set Prerequisites**: Define prerequisite courses required for enrollment
- **Enrollment Limits**: Set maximum number of students per course
- **Available Seats**: Track and display available seats for each course

#### Course Enrollment
- **Course Selection**: Dropdown menu for selecting available courses
- **Student Lists**: Separate lists for available and enrolled students
- **Bulk Enrollment**: Select and enroll multiple students simultaneously
- **Unenrollment**: Remove students from courses with confirmation
- **Prerequisite Checking**: Verify students meet prerequisites before enrollment
- **Capacity Management**: Prevent enrollment when courses reach capacity
- **Student Filtering**: Search functionality for both available and enrolled students

#### Grade Management
- **Student Selection**: Dropdown for choosing students
- **Course Overview**: Table showing all courses a student is enrolled in
- **Grade Assignment**: Assign grades from A+ to F scale
- **Grade History**: View current grades for all enrolled courses
- **GPA Calculation**: Automatic calculation and display of student GPA
- **Color-Coded Grades**: Visual indicators for different grade levels
- **Date Tracking**: Record and display when grades were assigned

## System Requirements
- **Java Version**: Java 8 or higher
- **Operating System**: Windows, macOS, or Linux
- **Memory**: Minimum 512 MB RAM
- **Display**: Minimum 1024x768 resolution

## Installation and Setup

#### Prerequisites
1. Ensure Java Development Kit (JDK) 8 or higher is installed
2. Verify Java installation by running: `java -version`

#### Compilation and Execution
1. **Compile the application after downloading the source code**:

   ```
   javac StudentManagementSystem.java
   ```
2. **Run the application**:

   ```bash
   java StudentManagementSystem
   ```

#### Alternative IDE Setup
1. **Eclipse/IntelliJ IDEA**:
   - Create a new Java project
   - Copy the source code into a new Java class file
   - Run the main method in StudentManagementSystem class

## User Guide
1. Launch the application by running the main class
2. The application opens with four tabs: Student Management, Course Management, Course Enrollment, and Grade Management
3. Sample data is automatically loaded for demonstration purposes

### Student Management Tab

##### Adding a New Student
1. Navigate to the "Student Management" tab
2. Fill in the required fields:
   - **Student ID**: Unique identifier (e.g., S001)
   - **Name**: Full name of the student
   - **Email**: Valid email address
   - **Major**: Student's field of study
3. Click "Add Student" button
4. Success message confirms the addition

##### Updating Student Information
1. Select a student from the table by clicking on the row
2. Student information automatically populates in the form fields
3. Modify the desired fields
4. Click "Update Student" button
5. Confirm the update in the success dialog

##### Viewing Student Details
- All students are displayed in the main table
- Click on column headers to sort by that field
- Use the scroll bar for large datasets

##### Searching for Students
1. Enter search terms in the search field
2. Click "Search" button or press Enter
3. Table updates to show only matching students
4. Click "Reset" to show all students again

##### Deleting a Student
1. Select the student from the table
2. Click "Delete Student" button
3. Confirm deletion in the dialog box
4. Student is removed from all related records

### Course Management Tab

##### Adding a New Course
1. Navigate to the "Course Management" tab
2. Fill in the required fields:
   - **Course ID**: Unique identifier (e.g., CS101)
   - **Course Name**: Name of the course
   - **Credits**: Number of credits for the course
   - **Enrollment Limit**: Maximum number of students allowed
3. Select any prerequisites from the list (optional)
4. Click "Add Course" button
5. Success message confirms the addition

##### Updating Course Information
1. Select a course from the table
2. Course information automatically populates in the form fields
3. Modify the desired fields
4. Click "Update Course" button
5. Confirm the update in the success dialog

##### Deleting a Course
1. Select the course from the table
2. Click "Delete Course" button
3. Confirm deletion in the dialog box
4. Course and all related enrollment records are removed

### Course Enrollment Tab

##### Enrolling Students in Courses
1. Navigate to the "Course Enrollment" tab
2. Select a course from the dropdown menu
3. Available students appear in the left list
4. Select one or more students from the available list
5. Click "Enroll Selected →" button
6. Students move to the enrolled list

##### Filtering Students
1. Use the filter fields above each list to search for specific students
2. Type in the filter field to dynamically update the list
3. Clear the filter field to show all students again

##### Unenrolling Students
1. Select enrolled students from the right list
2. Click "← Unenroll Selected" button
3. Confirm the action in the dialog
4. Students return to the available list

### Grade Management Tab

##### Assigning Grades
1. Navigate to the "Grade Management" tab
2. Select a student from the dropdown menu
3. Student's enrolled courses appear in the table
4. Select a course from the table
5. Choose a grade from the grade dropdown (A+ to F)
6. Click "Assign Grade" button
7. Grade is updated in the table and GPA recalculated

##### Viewing Student Grades
- Select any student to view their complete grade report
- Table shows course details and current grades
- "Not Assigned" appears for courses without grades
- GPA is displayed at the top right of the panel

## Technical Documentation
The Student Management System follows the Model-View-Controller (MVC) architectural pattern:
- **Model**: Data classes (Student, Course, Grade) and manager classes (StudentManager, CourseManager, GradeManager)
- **View**: GUI panels (StudentPanel, CourseManagementPanel, CoursePanel, GradePanel)
- **Controller**: Event handlers and action listeners that process user input

##### Model Classes
- **Student**: Data model for student information
- **Course**: Data model for course details with enrollment limits and prerequisites
- **Grade**: Data model for grade assignments with date tracking

##### Manager Classes
- **StudentManager**: Handles all student-related operations
- **CourseManager**: Manages courses, enrollments, and prerequisites
- **GradeManager**: Handles grade assignments, retrieval, and GPA calculation

##### View Classes
- **StudentManagementSystem**: Main application frame
- **StudentPanel**: GUI for student management
- **CourseManagementPanel**: GUI for course management
- **CoursePanel**: GUI for course enrollment
- **GradePanel**: GUI for grade management

### Design Patterns Observed

##### Observer Pattern
- Tab change listeners update data across panels
- Table selection listeners populate form fields
- Document listeners for search/filter functionality

##### Command Pattern
- Button actions encapsulated in ActionListener implementations
- Consistent error handling across all operations

##### Data Access Object (DAO) Pattern
- Manager classes abstract data operations
- Centralized data management and validation

##### Decorator Pattern
- Custom cell renderers enhance visual presentation
- Animated tabbed pane for smooth transitions

### Event Handling

##### Button Events

```java
// Example: Add Student button event handler
addButton.addActionListener(e -> addStudent());
```

##### Selection Events

```java
// Example: Table selection event handler
studentTable.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            populateFields(selectedRow);
        }
    }
});
```

##### Combo Box Events

```java
// Example: Course selection event handler
courseComboBox.addActionListener(e -> updateStudentLists());
```

##### Document Events

```java
// Example: Search field document listener
searchField.getDocument().addDocumentListener(new DocumentListener() {
    @Override
    public void insertUpdate(DocumentEvent e) { searchStudents(); }
    @Override
    public void removeUpdate(DocumentEvent e) { searchStudents(); }
    @Override
    public void changedUpdate(DocumentEvent e) { searchStudents(); }
});
```

### Data Structures

##### Student Storage
- `List<Student>` for maintaining student records
- Stream API for filtering and searching

##### Course Enrollment
- `Map<String, List<String>>` mapping course IDs to enrolled student IDs
- Efficient enrollment/unenrollment operations

##### Grade Management
- `List<Grade>` for storing all grade assignments
- Date tracking for grade history

## Design Choices

#### GUI Framework Selection
**Choice**: Java Swing
**Rationale**:
    - Native Java support without external dependencies
    - Mature and stable framework
    - Extensive component library
    - Cross-platform compatibility

#### Layout Management
**Choice**: Combination of BorderLayout, GridBagLayout, and FlowLayout
**Rationale**:
    - BorderLayout for main panel organization
    - GridBagLayout for precise form control
    - FlowLayout for button arrangements
    - Responsive design that adapts to window resizing

#### Data Management
**Choice**: In-memory data structures
**Rationale**:
    - Simplifies implementation for academic purposes
    - Fast data access and manipulation
    - No external database dependencies
    - Easy to understand and modify

#### User Interface Design
**Choice**: Tabbed interface with consistent styling
**Rationale**:
    - Logical separation of functionality
    - Familiar interface pattern
    - Easy navigation between modules
    - Consistent user experience

#### Validation Strategy

**Choice**: Client-side validation with immediate feedback
**Rationale**:
    - Immediate user feedback
    - Prevents invalid data entry
    - Reduces system errors
    - Better user experience

#### Visual Enhancements

**Choice**: Custom renderers and color coding
**Rationale**:
    - Improved visual hierarchy
    - Faster information processing
    - Better user engagement
    - Enhanced accessibility

## Error Handling

#### Input Validation
- **Required Field Validation**: Ensures all mandatory fields are filled
- **Format Validation**: Validates email format and data types
- **Duplicate Prevention**: Checks for existing student IDs and course IDs
- **Range Validation**: Ensures numeric fields are within valid ranges
- **Prerequisite Validation**: Verifies students meet course prerequisites

#### Exception Handling

```java
try {
    // Operation that might fail
    validateInput();
    performOperation();
    showSuccessMessage();
} catch (IllegalArgumentException e) {
    // Handle validation errors with specific guidance
    showErrorDialog("Input Error: " + e.getMessage() + "\n\nResolution: Please check your input fields.");
} catch (Exception e) {
    // Handle unexpected errors with technical details
    showErrorDialog("System Error: " + e.getMessage() + "\n\nResolution: Please contact technical support.");
    // Log for troubleshooting
    System.err.println("Error: " + e.getMessage());
    e.printStackTrace();
}
```

#### User Feedback
- **Success Messages**: Confirm successful operations with next steps
- **Error Dialogs**: Clear error descriptions with suggested resolutions
- **Confirmation Dialogs**: Prevent accidental data loss
- **Status Updates**: Real-time feedback in status bar
- **Visual Indicators**: Color coding for grades, GPA, and available seats

#### Graceful Degradation
- Application continues running even after errors
- Invalid operations are prevented rather than causing crashes
- Data integrity maintained through validation
- User can recover from errors without restarting

## Troubleshooting Guide

##### Application Won't Start
- **Issue**: Java Runtime Environment not found
- **Symptoms**: Error message about missing Java or "java command not found"
- **Solution**:
  1. Install Java Development Kit (JDK) 8 or higher
  2. Verify installation by running `java -version` in command prompt/terminal
  3. Ensure JAVA_HOME environment variable is set correctly

##### Student ID Already Exists
- **Issue**: Attempting to add a student with an ID that already exists
- **Symptoms**: Error dialog stating "Student ID already exists"
- **Solution**:
  1. Check the student table for existing IDs
  2. Use a different, unique ID for the new student
  3. If you need to modify an existing student, use the Update function instead

##### Cannot Enroll Student in Course
- **Issue**: Student cannot be enrolled in a course
- **Symptoms**: Student remains in the available list after attempting to enroll
- **Possible Causes and Solutions**:
  1. **Course at capacity**: Check if the course has reached its enrollment limit
     - Solution: Increase the enrollment limit in Course Management
  2. **Missing prerequisites**: Student may not have completed required prerequisites
     - Solution: Enroll the student in prerequisite courses first
  3. **System error**: Unexpected application behavior
     - Solution: Restart the application and try again

##### Grade Not Saving
- **Issue**: Assigned grade not appearing in the grade table
- **Symptoms**: Grade reverts to "Not Assigned" after assignment
- **Solution**:
  1. Ensure you've selected both a student and a course before assigning
  2. Click directly on the course row in the table before assigning
  3. Verify the student is properly enrolled in the course

##### Display Issues
- **Issue**: UI elements appear cut off or misaligned
- **Symptoms**: Buttons or fields not fully visible, layout problems
- **Solution**:
  1. Ensure screen resolution is at least 1024x768
  2. Resize the application window to be larger
  3. Adjust system display scaling settings

## Error Message Reference

| Error Message                                      | Possible Cause                   | Resolution                                    |
| -------------------------------------------------- | -------------------------------- | --------------------------------------------- |
| "Student ID already exists!"                       | Duplicate student ID             | Use a different, unique ID                    |
| "Course ID already exists!"                        | Duplicate course ID              | Use a different, unique ID                    |
| "Invalid email format!"                            | Email missing @ or domain        | Enter a valid email with proper format        |
| "Credits must be a positive number"                | Negative or zero credits value   | Enter a positive number for credits           |
| "Please select a student to update!"               | No student selected in table     | Click on a student row before updating        |
| "Enrollment Limit must be a valid number"          | Non-numeric value in limit field | Enter a numeric value for the limit           |
| "Cannot enroll X students. Only Y seats available" | Course at capacity               | Enroll fewer students or increase limit       |
| "The following students do not meet prerequisites" | Missing prerequisites            | Enroll students in prerequisite courses first |

## Developer Guide

#### Existing Class Hierarchy

```
StudentManagementSystem (JFrame)
├── AnimatedTabbedPane (JTabbedPane)
├── StudentPanel (JPanel)
├── CourseManagementPanel (JPanel)
├── CoursePanel (JPanel)
└── GradePanel (JPanel)

Data Models:
├── Student
├── Course
└── Grade

Managers:
├── StudentManager
├── CourseManager
└── GradeManager

Custom Components:
├── CourseListCellRenderer
├── StudentListCellRenderer
└── GradeCellRenderer
```

## Contributor's Guide to Extending the Application

##### Adding a New Feature
To add a new feature to the application:

1. **Identify the appropriate module**: Determine which existing panel should contain the feature, or if a new panel is needed
2. **Update data models**: Modify or create data classes as needed
3. **Update manager classes**: Add methods to handle the new functionality
4. **Create/update UI components**: Add necessary GUI elements
5. **Implement event handlers**: Add action listeners and event handling code
6. **Update documentation**: Document the new feature

##### Example: Adding a Search Feature

```java
// 1. Add search field to StudentPanel
private JTextField searchField;

// 2. Initialize in initializeComponents()
searchField = new JTextField(20);
JButton searchButton = new JButton("Search");

// 3. Add to layout
JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
searchPanel.add(new JLabel("Search:"));
searchPanel.add(searchField);
searchPanel.add(searchButton);
add(searchPanel, BorderLayout.SOUTH);

// 4. Add event handler
searchButton.addActionListener(e -> searchStudents());
searchField.addActionListener(e -> searchStudents());

// 5. Implement search functionality
private void searchStudents() {
    String query = searchField.getText().trim().toLowerCase();
    if (query.isEmpty()) {
        refreshTable(); // Show all if search is empty
        return;
    }
  
    tableModel.setRowCount(0);
    for (Student student : studentManager.getAllStudents()) {
        // Search in all fields
        if (student.getStudentId().toLowerCase().contains(query) ||
            student.getName().toLowerCase().contains(query) ||
            student.getEmail().toLowerCase().contains(query) ||
            student.getMajor().toLowerCase().contains(query)) {
    
            Object[] row = {
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getMajor()
            };
            tableModel.addRow(row);
        }
    }
}
```

### Project Best Practices for Modifications
1. **Maintain separation of concerns**: Keep data logic in manager classes, UI logic in panel classes
2. **Use consistent error handling**: Follow the established pattern for validation and error messages
3. **Update all affected components**: When modifying data, call appropriate refresh methods
4. **Document your changes**: Add comments explaining new code and update the README
5. **Test thoroughly**: Verify that your changes work correctly and don't break existing functionality

### Performance Considerations When Contemplating Additions
1. **Large datasets**: For applications with many students/courses, implement pagination or virtual scrolling
2. **Memory management**: Dispose of resources properly, especially when working with images or custom renderers
3. **UI responsiveness**: Perform long-running operations in background threads to keep the UI responsive
4. **Data persistence**: Consider implementing database storage for production use

## Refactoring for Production: Beyond the Assignment
While the current implementation works well for the assignment, in a real-world production environment, the code should be refactored to follow better software engineering practices. This section outlines how you could restructure the application for improved maintainability, scalability, and testability. Refactoring the project structure would enforce the following benefits:
1. **Improved Maintainability**: Smaller, focused files are easier to understand and modify
2. **Better Collaboration**: Multiple developers can work on different components simultaneously
3. **Enhanced Testability**: Isolated components are easier to test independently
4. **Clearer Dependencies**: Package structure makes dependencies explicit
5. **Separation of Concerns**: Clear distinction between UI, business logic, and data access

#### Implementation Strategy
To refactor the current monolithic application:
1. Extract model classes first (Student, Course, Grade)
2. Create service interfaces and implementations
3. Extract UI components into separate view classes
4. Implement controllers to connect views and services
5. Create utility classes for cross-cutting concerns
6. Update the main application class to use the new structure

```
com.sms/
├── model/                  # Data models
│   ├── Student.java
│   ├── Course.java
│   └── Grade.java
├── dao/                    # Data Access Objects
│   ├── StudentDAO.java
│   ├── CourseDAO.java
│   └── GradeDAO.java
├── service/                # Business logic
│   ├── StudentService.java
│   ├── CourseService.java
│   ├── EnrollmentService.java
│   └── GradeService.java
├── controller/             # Controllers connecting UI and services
│   ├── StudentController.java
│   ├── CourseController.java
│   ├── EnrollmentController.java
│   └── GradeController.java
├── view/                   # UI components
│   ├── MainFrame.java
│   ├── panel/
│   │   ├── StudentPanel.java
│   │   ├── CourseManagementPanel.java
│   │   ├── CourseEnrollmentPanel.java
│   │   └── GradePanel.java
│   ├── renderer/
│   │   ├── StudentListCellRenderer.java
│   │   ├── CourseListCellRenderer.java
│   │   └── GradeCellRenderer.java
│   └── component/
│       ├── AnimatedTabbedPane.java
│       └── SearchField.java
├── util/                   # Utility classes
│   ├── ValidationUtil.java
│   ├── FormatUtil.java
│   └── UIUtil.java
└── SMS.java                # Main application entry point
```

### Component Responsibilities

##### Model Layer
- **Student, Course, Grade**: Simple POJOs (Plain Old Java Objects) with properties and minimal logic

##### Data Access Layer
- **StudentDAO, CourseDAO, GradeDAO**: Handle data persistence operations (in a real app, these would connect to a database)

##### Service Layer
- **StudentService**: Business logic for student operations
- **CourseService**: Business logic for course operations
- **EnrollmentService**: Business logic for enrollment operations
- **GradeService**: Business logic for grade operations and GPA calculations

##### Controller Layer
- **StudentController, CourseController, etc.**: Coordinate between UI and services, handle events

##### View Layer
- **MainFrame**: Main application window
- **Panels**: Specialized UI components for different functions
- **Renderers**: Custom rendering for lists and tables
- **Components**: Reusable UI components

##### Utility Layer
- **ValidationUtil**: Input validation logic
- **FormatUtil**: Formatting utilities for display
- **UIUtil**: Common UI helper methods
