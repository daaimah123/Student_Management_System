import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * Main class for the Student Management System GUI Application
 * This class creates the main window and handles the overall application flow
 */
public class StudentManagementSystem extends JFrame {
    private StudentManager studentManager;
    private CourseManager courseManager;
    private GradeManager gradeManager;
    
    // GUI Components
    private AnimatedTabbedPane tabbedPane;
    private StudentPanel studentPanel;
    private CourseManagementPanel courseManagementPanel;
    private CoursePanel coursePanel;
    private GradePanel gradePanel;
    private JLabel statusBar;
    
    public StudentManagementSystem() {
        // Initialize managers
        studentManager = new StudentManager();
        courseManager = new CourseManager();
        gradeManager = new GradeManager();
        
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            customizeUIComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        initializeGUI();
        setupEventHandlers();
        loadSampleData();
        
        // Update status bar
        updateStatus("Application ready. Sample data loaded.");
    }
    
    /**
     * Customizes the appearance of UI components
     */
    private void customizeUIComponents() {
        // Customize button appearance
        UIManager.put("Button.background", new Color(230, 240, 250));
        UIManager.put("Button.foreground", new Color(0, 90, 150));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 12));
        
        // Customize table appearance
        UIManager.put("Table.alternateRowColor", new Color(240, 245, 250));
        UIManager.put("Table.selectionBackground", new Color(180, 200, 220));
        UIManager.put("Table.selectionForeground", new Color(0, 30, 60));
        
        // Customize tabbed pane
        UIManager.put("TabbedPane.selected", new Color(220, 235, 255));
        UIManager.put("TabbedPane.background", new Color(240, 240, 240));
        UIManager.put("TabbedPane.contentAreaColor", new Color(255, 255, 255));
    }
    
    /**
     * Initializes the main GUI components and layout
     */
    private void initializeGUI() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setIconImage(createImageIcon("🔷", "App Icon").getImage());
        
        // Create menu bar
        createMenuBar();
        
        // Create header panel with logo
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Add logo (placeholder or use an actual image)
        JLabel logoLabel = new JLabel("Student Management System", createImageIcon("🍎", "Logo"), JLabel.LEFT);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        logoLabel.setForeground(new Color(60, 60, 100));
        headerPanel.add(logoLabel, BorderLayout.WEST);
        
        // Add date/time
        JLabel dateTimeLabel = new JLabel(new SimpleDateFormat("EEEE, MMMM d, yyyy").format(new Date()));
        dateTimeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        headerPanel.add(dateTimeLabel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Create tabbed pane with animation
        tabbedPane = new AnimatedTabbedPane();
        
        // Initialize panels
        studentPanel = new StudentPanel(studentManager, this);
        courseManagementPanel = new CourseManagementPanel(courseManager, this);
        coursePanel = new CoursePanel(courseManager, studentManager, this);
        gradePanel = new GradePanel(gradeManager, studentManager, courseManager, this);
        
        // Add panels to tabbed pane with icons
        ImageIcon studentIcon = createImageIcon("🧑‍🎓", "Student Icon");
        ImageIcon courseIcon = createImageIcon("📚", "Course Icon");
        ImageIcon enrollIcon = createImageIcon("🍏", "Enrollment Icon");
        ImageIcon gradeIcon = createImageIcon("🙅‍♂️", "Grade Icon");
        
        tabbedPane.addTab("Student Management", studentIcon, studentPanel);
        tabbedPane.addTab("Course Management", courseIcon, courseManagementPanel);
        tabbedPane.addTab("Course Enrollment", enrollIcon, coursePanel);
        tabbedPane.addTab("Grade Management", gradeIcon, gradePanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Status bar
        statusBar = new JLabel("Ready");
        statusBar.setBorder(BorderFactory.createCompoundBorder(
            new BevelBorder(BevelBorder.LOWERED),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        add(statusBar, BorderLayout.SOUTH);
    }
    
    /**
     * Creates an ImageIcon from a resource path, or returns a default icon if the resource is not found
     */
    public ImageIcon createImageIcon(String pathOrEmoji, String description) {
        // Try to load as traditional image first
        java.net.URL imgURL = getClass().getResource(pathOrEmoji);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        }

        // If URL is null, treat it as an emoji
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        
        // Set white background and blue foreground
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 16, 16);
        g2d.setColor(new Color(100, 150, 200));
        
        // Draw emoji
        g2d.drawString(pathOrEmoji, 0, 14); // Simple positioning
        g2d.dispose();
        
        return new ImageIcon(img);
    }
    
    /**
     * Creates the menu bar with File, Edit, View, and Help menus
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        JMenuItem exportItem = new JMenuItem("Export Data...", createImageIcon("📤", "Export"));
        exportItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        exportItem.addActionListener(e -> showNotImplementedMessage("Export Data"));
        
        JMenuItem importItem = new JMenuItem("Import Data...", createImageIcon("📩", "Import"));
        importItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        importItem.addActionListener(e -> showNotImplementedMessage("Import Data"));
        
        JMenuItem exitItem = new JMenuItem("Exit", createImageIcon("🚪", "Exit"));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(exportItem);
        fileMenu.add(importItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // Edit menu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        
        JMenuItem preferencesItem = new JMenuItem("Preferences...", createImageIcon("⚙️", "Preferences"));
        preferencesItem.addActionListener(e -> showNotImplementedMessage("Preferences"));
        
        editMenu.add(preferencesItem);
        
        // View menu
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        
        JCheckBoxMenuItem statusBarItem = new JCheckBoxMenuItem("Status Bar");
        statusBarItem.setSelected(true);
        statusBarItem.addActionListener(e -> statusBar.setVisible(statusBarItem.isSelected()));
        
        viewMenu.add(statusBarItem);
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        
        JMenuItem helpContentsItem = new JMenuItem("Help Contents", createImageIcon("❓", "Help"));
        helpContentsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        helpContentsItem.addActionListener(e -> showNotImplementedMessage("Help Contents"));
        
        JMenuItem aboutItem = new JMenuItem("About", createImageIcon("ℹ️", "About"));
        aboutItem.addActionListener(e -> showAboutDialog());
        
        helpMenu.add(helpContentsItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    /**
     * Shows a dialog for features that are not yet implemented
     */
    private void showNotImplementedMessage(String feature) {
        JOptionPane.showMessageDialog(this,
            feature + " is not implemented in this version.\n" +
            "This feature will be available in a future update.",
            "Feature Not Available",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Sets up event handlers for cross-panel communication
     */
    private void setupEventHandlers() {
        // Add listener for tab changes to refresh data
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            switch (selectedIndex) {
                case 0: // Student Panel
                    updateStatus("Student Management: Manage student records");
                    break;
                case 1: // Course Management Panel
                    courseManagementPanel.refreshTable();
                    updateStatus("Course Management: Create and modify courses");
                    break;
                case 2: // Course Panel
                    coursePanel.refreshData();
                    updateStatus("Course Enrollment: Enroll students in courses");
                    break;
                case 3: // Grade Panel
                    gradePanel.refreshData();
                    updateStatus("Grade Management: Assign and view student grades");
                    break;
            }
        });
    }
    
    /**
     * Updates the status bar text
     */
    public void updateStatus(String message) {
        statusBar.setText(message);
    }
    
    /**
     * Loads sample data for demonstration purposes
     */
    private void loadSampleData() {
        // Add sample students
        studentManager.addStudent(new Student("S001", "John Doe", "john.doe@university.edu", "Computer Science"));
        studentManager.addStudent(new Student("S002", "Jane Smith", "jane.smith@university.edu", "Mathematics"));
        studentManager.addStudent(new Student("S003", "Bob Johnson", "bob.johnson@university.edu", "Physics"));
        studentManager.addStudent(new Student("S004", "Alice Williams", "alice.williams@university.edu", "Biology"));
        studentManager.addStudent(new Student("S005", "Charlie Brown", "charlie.brown@university.edu", "Chemistry"));
        
        // Add sample courses
        Course cs101 = new Course("CS101", "Introduction to Programming", 3, 30, new ArrayList<>());
        Course math201 = new Course("MATH201", "Calculus I", 4, 25, new ArrayList<>());
        Course phys101 = new Course("PHYS101", "General Physics", 3, 20, new ArrayList<>());
        Course bio101 = new Course("BIO101", "Introduction to Biology", 3, 25, new ArrayList<>());
        Course cs201 = new Course("CS201", "Data Structures", 4, 25, Arrays.asList("CS101"));
        
        courseManager.addCourse(cs101);
        courseManager.addCourse(math201);
        courseManager.addCourse(phys101);
        courseManager.addCourse(bio101);
        courseManager.addCourse(cs201);
        
        // Enroll students in courses
        courseManager.enrollStudent("CS101", "S001");
        courseManager.enrollStudent("CS101", "S002");
        courseManager.enrollStudent("MATH201", "S001");
        courseManager.enrollStudent("MATH201", "S002");
        courseManager.enrollStudent("MATH201", "S003");
        courseManager.enrollStudent("PHYS101", "S003");
        courseManager.enrollStudent("BIO101", "S004");
        
        // Assign grades
        gradeManager.assignGrade("S001", "CS101", "A");
        gradeManager.assignGrade("S002", "CS101", "B+");
        gradeManager.assignGrade("S001", "MATH201", "A-");
        gradeManager.assignGrade("S003", "PHYS101", "A+");
        
        // Refresh all panels
        studentPanel.refreshTable();
        courseManagementPanel.refreshTable();
        coursePanel.refreshData();
        gradePanel.refreshData();
    }
    
    /**
     * Shows the about dialog
     */
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "Student Management System v1.0\n\n" +
            "A comprehensive application for managing student records,\n" +
            "course enrollment, and grades.\n\n" +
            "Developed using Java Swing\n" +
            "© " + Calendar.getInstance().get(Calendar.YEAR) + " Student Management System",
            "About Student Management System",
            JOptionPane.INFORMATION_MESSAGE,
            createImageIcon("🔶", "Large App Icon"));
    }
    
    /**
     * Updates all panels when data changes
     */
    public void updateAllPanels() {
        studentPanel.refreshTable();
        courseManagementPanel.refreshTable();
        coursePanel.refreshData();
        gradePanel.refreshData();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new StudentManagementSystem().setVisible(true);
        });
    }
}

/**
 * Custom animated tabbed pane for smooth transitions between tabs
 */
class AnimatedTabbedPane extends JTabbedPane {
    // Declare variables first
    private float alpha = 0.0f;
    private int targetTab = -1;
    private Component currentComponent;
    private Component nextComponent;
    
    // Declare the timer but don't initialize it with a complex lambda yet
    private javax.swing.Timer animationTimer;
    
    public AnimatedTabbedPane() {
        super();
        
        // Initialize the timer in the constructor after all fields are declared
        animationTimer = new javax.swing.Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.1f;
                if (alpha >= 1.0f) {
                    alpha = 1.0f;
                    animationTimer.stop();
                    AnimatedTabbedPane.super.setSelectedIndex(targetTab);
                    repaint();
                } else {
                    repaint();
                }
            }
        });
    }
    
    @Override
    public void setSelectedIndex(int index) {
        if (index == getSelectedIndex()) {
            return;
        }
        
        if (animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        targetTab = index;
        alpha = 0.0f;
        currentComponent = getSelectedComponent();
        nextComponent = getComponentAt(index);
        
        animationTimer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (animationTimer.isRunning() && currentComponent != null && nextComponent != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            Rectangle bounds = getComponentRect(getSelectedIndex());
            
            // Draw current component fading out
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - alpha));
            paintComponent(g2d, currentComponent, bounds);
            
            // Draw next component fading in
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            paintComponent(g2d, nextComponent, bounds);
            
            g2d.dispose();
        }
    }
    
    private void paintComponent(Graphics2D g2d, Component component, Rectangle bounds) {
        BufferedImage img = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D imgG = img.createGraphics();
        component.paint(imgG);
        imgG.dispose();
        g2d.drawImage(img, bounds.x, bounds.y, null);
    }
    
    private Rectangle getComponentRect(int index) {
        Rectangle bounds = getBoundsAt(index);
        bounds.x = 0;
        bounds.y = bounds.height;
        bounds.width = getWidth();
        bounds.height = getHeight() - bounds.y;
        return bounds;
    }
}

/**
 * Student data model class
 */
class Student {
    private String studentId;
    private String name;
    private String email;
    private String major;
    
    public Student(String studentId, String name, String email, String major) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.major = major;
    }
    
    // Getters and setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    
    @Override
    public String toString() {
        return studentId + " - " + name;
    }
}

/**
 * Course data model class with enrollment limits and prerequisites
 */
class Course {
    private String courseId;
    private String courseName;
    private int credits;
    private int enrollmentLimit;
    private List<String> prerequisites;
    
    public Course(String courseId, String courseName, int credits) {
        this(courseId, courseName, credits, 30, new ArrayList<>());
    }
    
    public Course(String courseId, String courseName, int credits, int enrollmentLimit, List<String> prerequisites) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.enrollmentLimit = enrollmentLimit;
        this.prerequisites = prerequisites != null ? prerequisites : new ArrayList<>();
    }
    
    // Getters and setters
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public int getEnrollmentLimit() { return enrollmentLimit; }
    public void setEnrollmentLimit(int enrollmentLimit) { this.enrollmentLimit = enrollmentLimit; }
    public List<String> getPrerequisites() { return prerequisites; }
    public void setPrerequisites(List<String> prerequisites) { this.prerequisites = prerequisites; }
    public void addPrerequisite(String courseId) { this.prerequisites.add(courseId); }
    public void removePrerequisite(String courseId) { this.prerequisites.remove(courseId); }
    
    @Override
    public String toString() {
        return courseId + " - " + courseName;
    }
}

/**
 * Grade data model class
 */
class Grade {
    private String studentId;
    private String courseId;
    private String grade;
    private Date assignedDate;
    
    public Grade(String studentId, String courseId, String grade) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
        this.assignedDate = new Date();
    }
    
    // Getters and setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public Date getAssignedDate() { return assignedDate; }
    public void setAssignedDate(Date assignedDate) { this.assignedDate = assignedDate; }
}

/**
 * Manager class for student operations
 */
class StudentManager {
    private List<Student> students;
    
    public StudentManager() {
        students = new ArrayList<>();
    }
    
    public void addStudent(Student student) {
        students.add(student);
    }
    
    public void updateStudent(String studentId, Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(studentId)) {
                students.set(i, updatedStudent);
                break;
            }
        }
    }
    
    public void removeStudent(String studentId) {
        students.removeIf(student -> student.getStudentId().equals(studentId));
    }
    
    public Student getStudent(String studentId) {
        return students.stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
    
    public boolean studentExists(String studentId) {
        return students.stream().anyMatch(student -> student.getStudentId().equals(studentId));
    }
    
    public List<Student> searchStudents(String query) {
        query = query.toLowerCase();
        List<Student> results = new ArrayList<>();
        
        for (Student student : students) {
            if (student.getStudentId().toLowerCase().contains(query) ||
                student.getName().toLowerCase().contains(query) ||
                student.getEmail().toLowerCase().contains(query) ||
                student.getMajor().toLowerCase().contains(query)) {
                results.add(student);
            }
        }
        
        return results;
    }
}

/**
 * Manager class for course operations with enhanced functionality
 */
class CourseManager {
    private List<Course> courses;
    private Map<String, List<String>> enrollments; // courseId -> list of studentIds
    
    public CourseManager() {
        courses = new ArrayList<>();
        enrollments = new HashMap<>();
    }
    
    public void addCourse(Course course) {
        courses.add(course);
        enrollments.put(course.getCourseId(), new ArrayList<>());
    }
    
    public void updateCourse(String courseId, Course updatedCourse) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseId().equals(courseId)) {
                courses.set(i, updatedCourse);
                
                // If course ID changed, update enrollments map
                if (!courseId.equals(updatedCourse.getCourseId())) {
                    List<String> enrolledStudents = enrollments.remove(courseId);
                    enrollments.put(updatedCourse.getCourseId(), enrolledStudents != null ? enrolledStudents : new ArrayList<>());
                }
                break;
            }
        }
    }
    
    public void removeCourse(String courseId) {
        courses.removeIf(course -> course.getCourseId().equals(courseId));
        enrollments.remove(courseId);
    }
    
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }
    
    public Course getCourse(String courseId) {
        return courses.stream()
                .filter(course -> course.getCourseId().equals(courseId))
                .findFirst()
                .orElse(null);
    }
    
    public boolean courseExists(String courseId) {
        return courses.stream().anyMatch(course -> course.getCourseId().equals(courseId));
    }
    
    public void enrollStudent(String courseId, String studentId) {
        // Check enrollment limit
        Course course = getCourse(courseId);
        List<String> enrolled = enrollments.computeIfAbsent(courseId, k -> new ArrayList<>());
        
        if (course != null && enrolled.size() < course.getEnrollmentLimit()) {
            enrolled.add(studentId);
        }
    }
    
    public void unenrollStudent(String courseId, String studentId) {
        List<String> enrolled = enrollments.get(courseId);
        if (enrolled != null) {
            enrolled.remove(studentId);
        }
    }
    
    public List<String> getEnrolledStudents(String courseId) {
        return enrollments.getOrDefault(courseId, new ArrayList<>());
    }
    
    public List<String> getStudentCourses(String studentId) {
        List<String> studentCourses = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : enrollments.entrySet()) {
            if (entry.getValue().contains(studentId)) {
                studentCourses.add(entry.getKey());
            }
        }
        return studentCourses;
    }
    
    public boolean isStudentEnrolled(String courseId, String studentId) {
        List<String> enrolled = enrollments.get(courseId);
        return enrolled != null && enrolled.contains(studentId);
    }
    
    public boolean canEnrollStudent(String courseId, String studentId) {
        // Check if course has reached enrollment limit
        Course course = getCourse(courseId);
        if (course == null) return false;
        
        List<String> enrolledStudents = getEnrolledStudents(courseId);
        if (enrolledStudents.size() >= course.getEnrollmentLimit()) {
            return false;
        }
        
        // Check if student has completed prerequisites
        for (String prerequisiteId : course.getPrerequisites()) {
            if (!hasCompletedCourse(studentId, prerequisiteId)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean hasCompletedCourse(String studentId, String courseId) {
        // This would typically check grade records to see if student passed the course
        // For simplicity, we'll just check if they were ever enrolled
        List<String> studentCourses = getStudentCourses(studentId);
        return studentCourses.contains(courseId);
    }
    
    public int getAvailableSeats(String courseId) {
        Course course = getCourse(courseId);
        if (course == null) return 0;
        
        List<String> enrolledStudents = getEnrolledStudents(courseId);
        return course.getEnrollmentLimit() - enrolledStudents.size();
    }
}

/**
 * Manager class for grade operations
 */
class GradeManager {
    private List<Grade> grades;
    
    public GradeManager() {
        grades = new ArrayList<>();
    }
    
    public void assignGrade(String studentId, String courseId, String grade) {
        // Remove existing grade if any
        grades.removeIf(g -> g.getStudentId().equals(studentId) && g.getCourseId().equals(courseId));
        // Add new grade
        grades.add(new Grade(studentId, courseId, grade));
    }
    
    public String getGrade(String studentId, String courseId) {
        return grades.stream()
                .filter(grade -> grade.getStudentId().equals(studentId) && grade.getCourseId().equals(courseId))
                .map(Grade::getGrade)
                .findFirst()
                .orElse("Not Assigned");
    }
    
    public List<Grade> getStudentGrades(String studentId) {
        return grades.stream()
                .filter(grade -> grade.getStudentId().equals(studentId))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public List<Grade> getCourseGrades(String courseId) {
        return grades.stream()
                .filter(grade -> grade.getCourseId().equals(courseId))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public List<Grade> getAllGrades() {
        return new ArrayList<>(grades);
    }
    
    public double getGradePointValue(String grade) {
        switch (grade) {
            case "A+": return 4.0;
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D+": return 1.3;
            case "D": return 1.0;
            case "F": return 0.0;
            default: return 0.0;
        }
    }
    
    public double calculateGPA(String studentId, CourseManager courseManager) {
        List<Grade> studentGrades = getStudentGrades(studentId);
        if (studentGrades.isEmpty()) return 0.0;
        
        double totalPoints = 0.0;
        int totalCredits = 0;
        
        for (Grade grade : studentGrades) {
            Course course = courseManager.getCourse(grade.getCourseId());
            if (course != null) {
                double gradePoints = getGradePointValue(grade.getGrade());
                int credits = course.getCredits();
                
                totalPoints += gradePoints * credits;
                totalCredits += credits;
            }
        }
        
        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }
}

/**
 * Panel for student management functionality
 */
class StudentPanel extends JPanel {
    private StudentManager studentManager;
    private StudentManagementSystem mainFrame;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField idField, nameField, emailField, majorField, searchField;
    
    public StudentPanel(StudentManager studentManager, StudentManagementSystem mainFrame) {
        this.studentManager = studentManager;
        this.mainFrame = mainFrame;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        // Create table
        String[] columnNames = {"Student ID", "Name", "Email", "Major"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setRowHeight(25);
        studentTable.getTableHeader().setReorderingAllowed(false);
        
        // Set alternating row colors
        studentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 245, 250));
                }
                return c;
            }
        });
        
        // Create input fields
        idField = new JTextField(15);
        nameField = new JTextField(15);
        emailField = new JTextField(15);
        majorField = new JTextField(15);
        
        // Create search field
        searchField = new JTextField(20);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createEtchedBorder()
            ),
            "Student Information"
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Add form components
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 3;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Major:"), gbc);
        gbc.gridx = 3;
        formPanel.add(majorField, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        JButton addButton = new JButton("Add Student");
        addButton.setIcon(mainFrame.createImageIcon("➕", "Add"));
        
        JButton updateButton = new JButton("Update Student");
        updateButton.setIcon(mainFrame.createImageIcon("📝", "Update"));
        
        JButton deleteButton = new JButton("Delete Student");
        deleteButton.setIcon(mainFrame.createImageIcon("🗑️", "Delete"));
        
        JButton clearButton = new JButton("Clear Fields");
        clearButton.setIcon(mainFrame.createImageIcon("🆑", "Clear"));
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        
        JButton searchButton = new JButton("Search");
        searchButton.setIcon(mainFrame.createImageIcon("🔍", "Search"));
        searchPanel.add(searchButton);
        
        JButton resetButton = new JButton("Reset");
        resetButton.setIcon(mainFrame.createImageIcon("🔏", "Reset"));
        searchPanel.add(resetButton);
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createEtchedBorder()
        ));
        
        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        // Setup button actions
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearFields());
        searchButton.addActionListener(e -> searchStudents());
        resetButton.addActionListener(e -> {
            searchField.setText("");
            refreshTable();
        });
        
        // Add action listener to search field for Enter key
        searchField.addActionListener(e -> searchStudents());
    }
    
    private void setupEventHandlers() {
        // Table selection listener
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow >= 0) {
                    populateFields(selectedRow);
                }
            }
        });
    }
    
    private void addStudent() {
        try {
            validateInput();
            
            String id = idField.getText().trim();
            if (studentManager.studentExists(id)) {
                throw new IllegalArgumentException("Student ID already exists! Please choose a different ID.");
            }
            
            Student student = new Student(
                id,
                nameField.getText().trim(),
                emailField.getText().trim(),
                majorField.getText().trim()
            );
            
            studentManager.addStudent(student);
            refreshTable();
            clearFields();
            mainFrame.updateAllPanels();
            
            JOptionPane.showMessageDialog(this, 
                "Student added successfully!\nYou can now enroll this student in courses from the Course Enrollment tab.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            mainFrame.updateStatus("Student added: " + student.getName());
            
        } catch (IllegalArgumentException e) {
            // More specific error message with resolution guidance
            JOptionPane.showMessageDialog(this, 
                "Input Error: " + e.getMessage() + "\n\n" +
                "Resolution: Please check your input fields and ensure all required information is provided correctly.", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // General error with technical details and support guidance
            JOptionPane.showMessageDialog(this, 
                "System Error: An unexpected error occurred while adding the student.\n\n" +
                "Technical Details: " + e.getMessage() + "\n\n" +
                "Resolution: Please try again. If the problem persists, contact technical support with the error details above.", 
                "System Error", JOptionPane.ERROR_MESSAGE);
            
            // Log the error for technical support
            System.err.println("Error in addStudent: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void updateStudent() {
        try {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow < 0) {
                throw new IllegalArgumentException("Please select a student to update from the table below.");
            }
            
            validateInput();
            
            String originalId = (String) tableModel.getValueAt(selectedRow, 0);
            String newId = idField.getText().trim();
            
            // Check if ID is being changed and if new ID already exists
            if (!originalId.equals(newId) && studentManager.studentExists(newId)) {
                throw new IllegalArgumentException("Student ID already exists! Please choose a different ID.");
            }
            
            Student updatedStudent = new Student(
                newId,
                nameField.getText().trim(),
                emailField.getText().trim(),
                majorField.getText().trim()
            );
            
            studentManager.updateStudent(originalId, updatedStudent);
            refreshTable();
            clearFields();
            mainFrame.updateAllPanels();
            
            JOptionPane.showMessageDialog(this, 
                "Student updated successfully!\nAll enrollment and grade records have been maintained.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            mainFrame.updateStatus("Student updated: " + updatedStudent.getName());
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                "Input Error: " + e.getMessage() + "\n\n" +
                "Resolution: Please check your selection and input fields.", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "System Error: An unexpected error occurred while updating the student.\n\n" +
                "Technical Details: " + e.getMessage() + "\n\n" +
                "Resolution: Please try again or contact technical support.", 
                "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteStudent() {
        try {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow < 0) {
                throw new IllegalArgumentException("Please select a student to delete from the table below.");
            }
            
            String studentId = (String) tableModel.getValueAt(selectedRow, 0);
            String studentName = (String) tableModel.getValueAt(selectedRow, 1);
            
            int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete student: " + studentName + "?\n" +
                "This will remove all enrollment and grade records for this student.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (result == JOptionPane.YES_OPTION) {
                studentManager.removeStudent(studentId);
                refreshTable();
                clearFields();
                mainFrame.updateAllPanels();
                
                JOptionPane.showMessageDialog(this, 
                    "Student deleted successfully!\nAll related records have been removed.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                mainFrame.updateStatus("Student deleted: " + studentName);
            }
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                "Input Error: " + e.getMessage() + "\n\n" +
                "Resolution: Please select a student from the table.", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "System Error: An unexpected error occurred while deleting the student.\n\n" +
                "Technical Details: " + e.getMessage() + "\n\n" +
                "Resolution: Please try again or contact technical support.", 
                "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void searchStudents() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            refreshTable();
            return;
        }
        
        List<Student> searchResults = studentManager.searchStudents(query);
        
        tableModel.setRowCount(0);
        for (Student student : searchResults) {
            Object[] row = {
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getMajor()
            };
            tableModel.addRow(row);
        }
        
        mainFrame.updateStatus("Search results: " + searchResults.size() + " students found");
    }
    
    private void validateInput() {
        if (idField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID is required! Please enter a unique identifier.");
        }
        if (nameField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required! Please enter the student's full name.");
        }
        if (emailField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required! Please enter a valid email address.");
        }
        if (majorField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Major is required! Please enter the student's field of study.");
        }
        
        // Enhanced email validation with specific guidance
        String email = emailField.getText().trim();
        if (!email.contains("@") || !email.contains(".") || email.indexOf("@") > email.lastIndexOf(".")) {
            throw new IllegalArgumentException(
                "Invalid email format! Please enter a valid email address (e.g., student@university.edu)."
            );
        }
    }
    
    private void populateFields(int row) {
        idField.setText((String) tableModel.getValueAt(row, 0));
        nameField.setText((String) tableModel.getValueAt(row, 1));
        emailField.setText((String) tableModel.getValueAt(row, 2));
        majorField.setText((String) tableModel.getValueAt(row, 3));
    }
    
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        majorField.setText("");
        studentTable.clearSelection();
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Student student : studentManager.getAllStudents()) {
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

/**
 * Panel for course management functionality
 */
class CourseManagementPanel extends JPanel {
    private CourseManager courseManager;
    private StudentManagementSystem mainFrame;
    private JTable courseTable;
    private DefaultTableModel tableModel;
    private JTextField idField, nameField, creditsField, limitField;
    private JList<Course> prerequisitesList;
    private DefaultListModel<Course> prerequisitesModel;
    
    public CourseManagementPanel(CourseManager courseManager, StudentManagementSystem mainFrame) {
        this.courseManager = courseManager;
        this.mainFrame = mainFrame;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        // Create table
        String[] columnNames = {"Course ID", "Course Name", "Credits", "Enrollment Limit", "Prerequisites", "Available Seats"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        courseTable = new JTable(tableModel);
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseTable.setRowHeight(25);
        courseTable.getTableHeader().setReorderingAllowed(false);
        
        // Set alternating row colors
        courseTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 245, 250));
                }
                
                // Highlight available seats column
                if (column == 5 && value != null) {
                    int seats = Integer.parseInt(value.toString());
                    if (seats <= 0) {
                        c.setForeground(Color.RED);
                    } else if (seats < 5) {
                        c.setForeground(new Color(255, 150, 0)); // Orange
                    } else {
                        c.setForeground(new Color(0, 150, 0)); // Green
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                } else {
                    c.setForeground(table.getForeground());
                    setHorizontalAlignment(JLabel.LEFT);
                }
                
                return c;
            }
        });
        
        // Create input fields
        idField = new JTextField(15);
        nameField = new JTextField(15);
        creditsField = new JTextField(15);
        limitField = new JTextField(15);
        
        // Create prerequisites list
        prerequisitesModel = new DefaultListModel<>();
        prerequisitesList = new JList<>(prerequisitesModel);
        prerequisitesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        prerequisitesList.setCellRenderer(new CourseListCellRenderer());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createEtchedBorder()
            ),
            "Course Information"
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Add form components
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Course ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Course Name:"), gbc);
        gbc.gridx = 3;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Credits:"), gbc);
        gbc.gridx = 1;
        formPanel.add(creditsField, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Enrollment Limit:"), gbc);
        gbc.gridx = 3;
        formPanel.add(limitField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Prerequisites:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.gridheight = 2;
        JScrollPane prereqScroll = new JScrollPane(prerequisitesList);
        prereqScroll.setPreferredSize(new Dimension(200, 100));
        formPanel.add(prereqScroll, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        JButton addButton = new JButton("Add Course");
        addButton.setIcon(mainFrame.createImageIcon("➕", "Add"));
        
        JButton updateButton = new JButton("Update Course");
        updateButton.setIcon(mainFrame.createImageIcon("📝", "Update"));
        
        JButton deleteButton = new JButton("Delete Course");
        deleteButton.setIcon(mainFrame.createImageIcon("🗑️", "Delete"));
        
        JButton clearButton = new JButton("Clear Fields");
        clearButton.setIcon(mainFrame.createImageIcon("🆑", "Clear"));
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createEtchedBorder()
        ));
        
        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Setup button actions
        addButton.addActionListener(e -> addCourse());
        updateButton.addActionListener(e -> updateCourse());
        deleteButton.addActionListener(e -> deleteCourse());
        clearButton.addActionListener(e -> clearFields());
    }
    
    private void setupEventHandlers() {
        // Table selection listener
        courseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = courseTable.getSelectedRow();
                if (selectedRow >= 0) {
                    populateFields(selectedRow);
                }
            }
        });
    }
    
    private void addCourse() {
        try {
            validateInput();
            
            String id = idField.getText().trim();
            if (courseManager.courseExists(id)) {
                throw new IllegalArgumentException("Course ID already exists! Please choose a different ID.");
            }
            
            int credits = Integer.parseInt(creditsField.getText().trim());
            int limit = Integer.parseInt(limitField.getText().trim());
            
            // Get selected prerequisites
            List<String> prerequisites = new ArrayList<>();
            for (int i = 0; i < prerequisitesList.getSelectedIndices().length; i++) {
                Course prereq = prerequisitesModel.getElementAt(prerequisitesList.getSelectedIndices()[i]);
                prerequisites.add(prereq.getCourseId());
            }
            
            Course course = new Course(
                id,
                nameField.getText().trim(),
                credits,
                limit,
                prerequisites
            );
            
            courseManager.addCourse(course);
            refreshTable();
            clearFields();
            mainFrame.updateAllPanels();
            
            JOptionPane.showMessageDialog(this, 
                "Course added successfully!\nYou can now enroll students in this course from the Course Enrollment tab.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            mainFrame.updateStatus("Course added: " + course.getCourseName());
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Invalid number format: Credits and Enrollment Limit must be numeric values.\n\n" +
                "Resolution: Please enter whole numbers only.", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error adding course: " + e.getMessage() + "\n\n" +
                "Resolution: Please check your input and try again.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateCourse() {
        try {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow < 0) {
                throw new IllegalArgumentException("Please select a course to update from the table below.");
            }
            
            validateInput();
            
            String originalId = (String) tableModel.getValueAt(selectedRow, 0);
            String newId = idField.getText().trim();
            
            // Check if ID is being changed and if new ID already exists
            if (!originalId.equals(newId) && courseManager.courseExists(newId)) {
                throw new IllegalArgumentException("Course ID already exists! Please choose a different ID.");
            }
            
            int credits = Integer.parseInt(creditsField.getText().trim());
            int limit = Integer.parseInt(limitField.getText().trim());
            
            // Get selected prerequisites
            List<String> prerequisites = new ArrayList<>();
            for (int i = 0; i < prerequisitesList.getSelectedIndices().length; i++) {
                Course prereq = prerequisitesModel.getElementAt(prerequisitesList.getSelectedIndices()[i]);
                prerequisites.add(prereq.getCourseId());
            }
            
            Course updatedCourse = new Course(
                newId,
                nameField.getText().trim(),
                credits,
                limit,
                prerequisites
            );
            
            courseManager.updateCourse(originalId, updatedCourse);
            refreshTable();
            clearFields();
            mainFrame.updateAllPanels();
            
            JOptionPane.showMessageDialog(this, 
                "Course updated successfully!\nAll enrollment records have been maintained.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            mainFrame.updateStatus("Course updated: " + updatedCourse.getCourseName());
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Invalid number format: Credits and Enrollment Limit must be numeric values.\n\n" +
                "Resolution: Please enter whole numbers only.", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error updating course: " + e.getMessage() + "\n\n" +
                "Resolution: Please check your input and try again.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteCourse() {
        try {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow < 0) {
                throw new IllegalArgumentException("Please select a course to delete from the table below.");
            }
            
            String courseId = (String) tableModel.getValueAt(selectedRow, 0);
            String courseName = (String) tableModel.getValueAt(selectedRow, 1);
            
            int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete course: " + courseName + "?\n" +
                "This will remove all enrollment records for this course.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (result == JOptionPane.YES_OPTION) {
                courseManager.removeCourse(courseId);
                refreshTable();
                clearFields();
                mainFrame.updateAllPanels();
                
                JOptionPane.showMessageDialog(this, 
                    "Course deleted successfully!\nAll related enrollment records have been removed.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                mainFrame.updateStatus("Course deleted: " + courseName);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error deleting course: " + e.getMessage() + "\n\n" +
                "Resolution: Please try again or contact technical support if the issue persists.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void validateInput() {
        if (idField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID is required! Please enter a unique identifier.");
        }
        if (nameField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Course Name is required! Please enter a descriptive name.");
        }
        if (creditsField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Credits is required! Please enter a numeric value.");
        }
        if (limitField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Enrollment Limit is required! Please enter a numeric value.");
        }
        
        // Validate numeric fields
        try {
            int credits = Integer.parseInt(creditsField.getText().trim());
            if (credits <= 0) {
                throw new IllegalArgumentException("Credits must be a positive number.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Credits must be a valid number.");
        }
        
        try {
            int limit = Integer.parseInt(limitField.getText().trim());
            if (limit <= 0) {
                throw new IllegalArgumentException("Enrollment Limit must be a positive number.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Enrollment Limit must be a valid number.");
        }
    }
    
    private void populateFields(int row) {
        String courseId = (String) tableModel.getValueAt(row, 0);
        Course course = courseManager.getCourse(courseId);
        
        idField.setText(course.getCourseId());
        nameField.setText(course.getCourseName());
        creditsField.setText(String.valueOf(course.getCredits()));
        limitField.setText(String.valueOf(course.getEnrollmentLimit()));
        
        // Select prerequisites in the list
        prerequisitesList.clearSelection();
        List<String> prereqs = course.getPrerequisites();
        for (int i = 0; i < prerequisitesModel.size(); i++) {
            Course c = prerequisitesModel.getElementAt(i);
            if (prereqs.contains(c.getCourseId())) {
                prerequisitesList.addSelectionInterval(i, i);
            }
        }
    }
    
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        creditsField.setText("");
        limitField.setText("30"); // Default value
        prerequisitesList.clearSelection();
        courseTable.clearSelection();
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        
        // Update prerequisites list model
        prerequisitesModel.clear();
        for (Course course : courseManager.getAllCourses()) {
            prerequisitesModel.addElement(course);
        }
        
        // Update table
        for (Course course : courseManager.getAllCourses()) {
            StringBuilder prereqsStr = new StringBuilder();
            for (String prereqId : course.getPrerequisites()) {
                Course prereq = courseManager.getCourse(prereqId);
                if (prereq != null) {
                    if (prereqsStr.length() > 0) {
                        prereqsStr.append(", ");
                    }
                    prereqsStr.append(prereq.getCourseId());
                }
            }
            
            int availableSeats = courseManager.getAvailableSeats(course.getCourseId());
            
            Object[] row = {
                course.getCourseId(),
                course.getCourseName(),
                course.getCredits(),
                course.getEnrollmentLimit(),
                prereqsStr.toString(),
                availableSeats
            };
            tableModel.addRow(row);
        }
    }
}

/**
 * Custom renderer for the prerequisites list
 */
class CourseListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                 boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof Course) {
            Course course = (Course) value;
            label.setText(course.getCourseId() + " - " + course.getCourseName() + " (" + course.getCredits() + " credits)");
            label.setToolTipText("Enrollment Limit: " + course.getEnrollmentLimit());
        }
        
        return label;
    }
}

/**
 * Panel for course enrollment functionality
 */
class CoursePanel extends JPanel {
    private CourseManager courseManager;
    private StudentManager studentManager;
    private StudentManagementSystem mainFrame;
    private JComboBox<Course> courseComboBox;
    private JList<Student> availableStudentsList;
    private JList<Student> enrolledStudentsList;
    private DefaultListModel<Student> availableListModel;
    private DefaultListModel<Student> enrolledListModel;
    private JLabel enrollmentInfoLabel;
    
    public CoursePanel(CourseManager courseManager, StudentManager studentManager, 
                      StudentManagementSystem mainFrame) {
        this.courseManager = courseManager;
        this.studentManager = studentManager;
        this.mainFrame = mainFrame;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        courseComboBox = new JComboBox<>();
        
        availableListModel = new DefaultListModel<>();
        enrolledListModel = new DefaultListModel<>();
        
        availableStudentsList = new JList<>(availableListModel);
        enrolledStudentsList = new JList<>(enrolledListModel);
        
        availableStudentsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        enrolledStudentsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        // Custom cell renderer for student lists
        StudentListCellRenderer renderer = new StudentListCellRenderer();
        availableStudentsList.setCellRenderer(renderer);
        enrolledStudentsList.setCellRenderer(renderer);
        
        enrollmentInfoLabel = new JLabel("Select a course to view enrollment information");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top panel for course selection
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.add(new JLabel("Select Course:"));
        selectionPanel.add(courseComboBox);
        
        topPanel.add(selectionPanel, BorderLayout.WEST);
        topPanel.add(enrollmentInfoLabel, BorderLayout.EAST);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createEtchedBorder()
        ));
        
        // Center panel with two lists
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Available students panel
        JPanel availablePanel = new JPanel(new BorderLayout());
        availablePanel.setBorder(BorderFactory.createTitledBorder("Available Students"));
        
        // Add search field for available students
        JPanel availableSearchPanel = new JPanel(new BorderLayout());
        JTextField availableSearchField = new JTextField();
        availableSearchField.setToolTipText("Type to filter available students");
        availableSearchPanel.add(new JLabel("Filter:"), BorderLayout.WEST);
        availableSearchPanel.add(availableSearchField, BorderLayout.CENTER);
        
        availablePanel.add(availableSearchPanel, BorderLayout.NORTH);
        availablePanel.add(new JScrollPane(availableStudentsList), BorderLayout.CENTER);
        
        // Enrolled students panel
        JPanel enrolledPanel = new JPanel(new BorderLayout());
        enrolledPanel.setBorder(BorderFactory.createTitledBorder("Enrolled Students"));
        
        // Add search field for enrolled students
        JPanel enrolledSearchPanel = new JPanel(new BorderLayout());
        JTextField enrolledSearchField = new JTextField();
        enrolledSearchField.setToolTipText("Type to filter enrolled students");
        enrolledSearchPanel.add(new JLabel("Filter:"), BorderLayout.WEST);
        enrolledSearchPanel.add(enrolledSearchField, BorderLayout.CENTER);
        
        enrolledPanel.add(enrolledSearchPanel, BorderLayout.NORTH);
        enrolledPanel.add(new JScrollPane(enrolledStudentsList), BorderLayout.CENTER);
        
        centerPanel.add(availablePanel);
        centerPanel.add(enrolledPanel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton enrollButton = new JButton("Enroll Selected →");
        enrollButton.setIcon(mainFrame.createImageIcon("🍏", "Enroll"));
        
        JButton unenrollButton = new JButton("← Unenroll Selected");
        unenrollButton.setIcon(mainFrame.createImageIcon("🚷", "Unenroll"));
        
        buttonPanel.add(enrollButton);
        buttonPanel.add(unenrollButton);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Setup button actions
        enrollButton.addActionListener(e -> enrollSelectedStudents());
        unenrollButton.addActionListener(e -> unenrollSelectedStudents());
        
        // Setup search functionality
        availableSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterAvailableStudents(availableSearchField.getText()); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterAvailableStudents(availableSearchField.getText()); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterAvailableStudents(availableSearchField.getText()); }
        });
        
        enrolledSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterEnrolledStudents(enrolledSearchField.getText()); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterEnrolledStudents(enrolledSearchField.getText()); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterEnrolledStudents(enrolledSearchField.getText()); }
        });
    }
    
    private void setupEventHandlers() {
        courseComboBox.addActionListener(e -> updateStudentLists());
    }
    
    private void filterAvailableStudents(String filter) {
        if (filter.isEmpty()) {
            updateStudentLists(); // Reset to full list
            return;
        }
        
        filter = filter.toLowerCase();
        Course selectedCourse = (Course) courseComboBox.getSelectedItem();
        if (selectedCourse == null) return;
        
        List<String> enrolledStudentIds = courseManager.getEnrolledStudents(selectedCourse.getCourseId());
        List<Student> allStudents = studentManager.getAllStudents();
        
        availableListModel.clear();
        for (Student student : allStudents) {
            if (!enrolledStudentIds.contains(student.getStudentId()) && 
                (student.getStudentId().toLowerCase().contains(filter) || 
                 student.getName().toLowerCase().contains(filter) ||
                 student.getMajor().toLowerCase().contains(filter))) {
                availableListModel.addElement(student);
            }
        }
    }
    
    private void filterEnrolledStudents(String filter) {
        if (filter.isEmpty()) {
            updateStudentLists(); // Reset to full list
            return;
        }
        
        filter = filter.toLowerCase();
        Course selectedCourse = (Course) courseComboBox.getSelectedItem();
        if (selectedCourse == null) return;
        
        List<String> enrolledStudentIds = courseManager.getEnrolledStudents(selectedCourse.getCourseId());
        
        enrolledListModel.clear();
        for (String studentId : enrolledStudentIds) {
            Student student = studentManager.getStudent(studentId);
            if (student != null && 
                (student.getStudentId().toLowerCase().contains(filter) || 
                 student.getName().toLowerCase().contains(filter) ||
                 student.getMajor().toLowerCase().contains(filter))) {
                enrolledListModel.addElement(student);
            }
        }
    }
    
    private void enrollSelectedStudents() {
        try {
            Course selectedCourse = (Course) courseComboBox.getSelectedItem();
            if (selectedCourse == null) {
                throw new IllegalArgumentException("Please select a course!");
            }
            
            List<Student> selectedStudents = availableStudentsList.getSelectedValuesList();
            if (selectedStudents.isEmpty()) {
                throw new IllegalArgumentException("Please select students to enroll!");
            }
            
            // Check enrollment limit
            int availableSeats = courseManager.getAvailableSeats(selectedCourse.getCourseId());
            if (selectedStudents.size() > availableSeats) {
                throw new IllegalArgumentException(
                    "Cannot enroll " + selectedStudents.size() + " students. Only " + 
                    availableSeats + " seats available in this course."
                );
            }
            
            // Check prerequisites for each student
            List<Student> studentsWithoutPrereqs = new ArrayList<>();
            for (Student student : selectedStudents) {
                if (!courseManager.canEnrollStudent(selectedCourse.getCourseId(), student.getStudentId())) {
                    studentsWithoutPrereqs.add(student);
                }
            }
            
            if (!studentsWithoutPrereqs.isEmpty()) {
                StringBuilder message = new StringBuilder("The following students do not meet prerequisites:\n\n");
                for (Student student : studentsWithoutPrereqs) {
                    message.append("- ").append(student.getName()).append(" (").append(student.getStudentId()).append(")\n");
                }
                message.append("\nWould you like to enroll the eligible students only?");
                
                int result = JOptionPane.showConfirmDialog(this, message.toString(), 
                    "Prerequisite Check", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
                if (result == JOptionPane.NO_OPTION) {
                    return;
                }
                
                // Remove students without prerequisites
                selectedStudents.removeAll(studentsWithoutPrereqs);
                if (selectedStudents.isEmpty()) {
                    throw new IllegalArgumentException("No eligible students to enroll!");
                }
            }
            
            // Enroll eligible students
            for (Student student : selectedStudents) {
                courseManager.enrollStudent(selectedCourse.getCourseId(), student.getStudentId());
            }
            
            updateStudentLists();
            mainFrame.updateAllPanels();
            
            JOptionPane.showMessageDialog(this, 
                selectedStudents.size() + " student(s) enrolled successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            mainFrame.updateStatus(selectedStudents.size() + " students enrolled in " + selectedCourse.getCourseId());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error enrolling students: " + e.getMessage() + "\n\n" +
                "Resolution: Please check course capacity and prerequisites.", 
                "Enrollment Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void unenrollSelectedStudents() {
        try {
            Course selectedCourse = (Course) courseComboBox.getSelectedItem();
            if (selectedCourse == null) {
                throw new IllegalArgumentException("Please select a course!");
            }
            
            List<Student> selectedStudents = enrolledStudentsList.getSelectedValuesList();
            if (selectedStudents.isEmpty()) {
                throw new IllegalArgumentException("Please select students to unenroll!");
            }
            
            int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to unenroll " + selectedStudents.size() + " student(s)?\n" +
                "This may affect their ability to enroll in courses that have this as a prerequisite.",
                "Confirm Unenroll", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (result == JOptionPane.YES_OPTION) {
                for (Student student : selectedStudents) {
                    courseManager.unenrollStudent(selectedCourse.getCourseId(), student.getStudentId());
                }
                
                updateStudentLists();
                mainFrame.updateAllPanels();
                
                JOptionPane.showMessageDialog(this, 
                    selectedStudents.size() + " student(s) unenrolled successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                mainFrame.updateStatus(selectedStudents.size() + " students unenrolled from " + selectedCourse.getCourseId());
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error unenrolling students: " + e.getMessage() + "\n\n" +
                "Resolution: Please try again or contact technical support if the issue persists.", 
                "Unenrollment Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateStudentLists() {
        Course selectedCourse = (Course) courseComboBox.getSelectedItem();
        if (selectedCourse == null) {
            availableListModel.clear();
            enrolledListModel.clear();
            enrollmentInfoLabel.setText("Select a course to view enrollment information");
            return;
        }
        
        List<String> enrolledStudentIds = courseManager.getEnrolledStudents(selectedCourse.getCourseId());
        List<Student> allStudents = studentManager.getAllStudents();
        
        // Update available students list
        availableListModel.clear();
        for (Student student : allStudents) {
            if (!enrolledStudentIds.contains(student.getStudentId())) {
                availableListModel.addElement(student);
            }
        }
        
        // Update enrolled students list
        enrolledListModel.clear();
        for (String studentId : enrolledStudentIds) {
            Student student = studentManager.getStudent(studentId);
            if (student != null) {
                enrolledListModel.addElement(student);
            }
        }
        
        // Update enrollment info label
        int availableSeats = courseManager.getAvailableSeats(selectedCourse.getCourseId());
        enrollmentInfoLabel.setText(
            "Enrollment: " + enrolledStudentIds.size() + "/" + selectedCourse.getEnrollmentLimit() + 
            " (" + availableSeats + " seats available)"
        );
        
        if (availableSeats <= 0) {
            enrollmentInfoLabel.setForeground(Color.RED);
        } else if (availableSeats < 5) {
            enrollmentInfoLabel.setForeground(new Color(255, 150, 0)); // Orange
        } else {
            enrollmentInfoLabel.setForeground(new Color(0, 150, 0)); // Green
        }
    }
    
    public void refreshData() {
        // Refresh course combo box
        Course selectedCourse = (Course) courseComboBox.getSelectedItem();
        String selectedCourseId = selectedCourse != null ? selectedCourse.getCourseId() : null;
        
        courseComboBox.removeAllItems();
        for (Course course : courseManager.getAllCourses()) {
            courseComboBox.addItem(course);
            if (course.getCourseId().equals(selectedCourseId)) {
                courseComboBox.setSelectedItem(course);
            }
        }
        
        updateStudentLists();
    }
}

/**
 * Custom renderer for student lists
 */
class StudentListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                 boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof Student) {
            Student student = (Student) value;
            label.setText(student.getStudentId() + " - " + student.getName() + " (" + student.getMajor() + ")");
        }
        
        return label;
    }
}

/**
 * Panel for grade management functionality
 */
class GradePanel extends JPanel {
    private GradeManager gradeManager;
    private StudentManager studentManager;
    private CourseManager courseManager;
    private StudentManagementSystem mainFrame;
    private JComboBox<Student> studentComboBox;
    private JTable gradeTable;
    private DefaultTableModel gradeTableModel;
    private JComboBox<String> gradeComboBox;
    private JLabel gpaLabel;
    
    public GradePanel(GradeManager gradeManager, StudentManager studentManager, 
                     CourseManager courseManager, StudentManagementSystem mainFrame) {
        this.gradeManager = gradeManager;
        this.studentManager = studentManager;
        this.courseManager = courseManager;
        this.mainFrame = mainFrame;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        studentComboBox = new JComboBox<>();
        
        String[] columnNames = {"Course ID", "Course Name", "Credits", "Grade", "Date Assigned"};
        gradeTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        gradeTable = new JTable(gradeTableModel);
        gradeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gradeTable.setRowHeight(25);
        gradeTable.getTableHeader().setReorderingAllowed(false);
        
        // Set custom renderer for the grade column
        gradeTable.getColumnModel().getColumn(3).setCellRenderer(new GradeCellRenderer());
        
        String[] grades = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"};
        gradeComboBox = new JComboBox<>(grades);
        
        gpaLabel = new JLabel("GPA: 0.00");
        gpaLabel.setFont(new Font("Arial", Font.BOLD, 14));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top panel for student selection
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.add(new JLabel("Select Student:"));
        selectionPanel.add(studentComboBox);
        
        topPanel.add(selectionPanel, BorderLayout.WEST);
        topPanel.add(gpaLabel, BorderLayout.EAST);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createEtchedBorder()
        ));
        
        // Center panel with grade table
        JScrollPane scrollPane = new JScrollPane(gradeTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createEtchedBorder()
        ));
        
        // Bottom panel for grade assignment
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        bottomPanel.add(new JLabel("Assign Grade:"));
        bottomPanel.add(gradeComboBox);
        
        JButton assignButton = new JButton("Assign Grade");
        assignButton.setIcon(mainFrame.createImageIcon("📈", "Grade"));
        bottomPanel.add(assignButton);
        
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createEtchedBorder()
        ));
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Setup button action
        assignButton.addActionListener(e -> assignGrade());
    }
    
    private void setupEventHandlers() {
        studentComboBox.addActionListener(e -> updateGradeTable());
    }
    
    private void assignGrade() {
        try {
            Student selectedStudent = (Student) studentComboBox.getSelectedItem();
            if (selectedStudent == null) {
                throw new IllegalArgumentException("Please select a student!");
            }
            
            int selectedRow = gradeTable.getSelectedRow();
            if (selectedRow < 0) {
                throw new IllegalArgumentException("Please select a course from the table!");
            }
            
            String courseId = (String) gradeTableModel.getValueAt(selectedRow, 0);
            String selectedGrade = (String) gradeComboBox.getSelectedItem();
            
            gradeManager.assignGrade(selectedStudent.getStudentId(), courseId, selectedGrade);
            updateGradeTable();
            
            JOptionPane.showMessageDialog(this, 
                "Grade assigned successfully!\nThe student's GPA has been updated.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            mainFrame.updateStatus("Grade assigned: " + selectedGrade + " for " + selectedStudent.getName() + " in " + courseId);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error assigning grade: " + e.getMessage() + "\n\n" +
                "Resolution: Please ensure you've selected both a student and a course.", 
                "Grade Assignment Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateGradeTable() {
        Student selectedStudent = (Student) studentComboBox.getSelectedItem();
        if (selectedStudent == null) {
            gradeTableModel.setRowCount(0);
            gpaLabel.setText("GPA: 0.00");
            return;
        }
        
        gradeTableModel.setRowCount(0);
        List<String> studentCourses = courseManager.getStudentCourses(selectedStudent.getStudentId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        
        for (String courseId : studentCourses) {
            Course course = courseManager.getCourse(courseId);
            if (course != null) {
                String grade = gradeManager.getGrade(selectedStudent.getStudentId(), courseId);
                
                // Get date assigned if available
                String dateAssigned = "N/A";
                for (Grade g : gradeManager.getStudentGrades(selectedStudent.getStudentId())) {
                    if (g.getCourseId().equals(courseId)) {
                        dateAssigned = dateFormat.format(g.getAssignedDate());
                        break;
                    }
                }
                
                Object[] row = {
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getCredits(),
                    grade,
                    dateAssigned
                };
                gradeTableModel.addRow(row);
            }
        }
        
        // Update GPA
        double gpa = gradeManager.calculateGPA(selectedStudent.getStudentId(), courseManager);
        gpaLabel.setText(String.format("GPA: %.2f", gpa));
        
        // Color code the GPA
        if (gpa >= 3.5) {
            gpaLabel.setForeground(new Color(0, 150, 0)); // Green for high GPA
        } else if (gpa >= 2.0) {
            gpaLabel.setForeground(new Color(0, 0, 150)); // Blue for average GPA
        } else {
            gpaLabel.setForeground(new Color(200, 0, 0)); // Red for low GPA
        }
    }
    
    public void refreshData() {
        // Refresh student combo box
        Student selectedStudent = (Student) studentComboBox.getSelectedItem();
        String selectedStudentId = selectedStudent != null ? selectedStudent.getStudentId() : null;
        
        studentComboBox.removeAllItems();
        for (Student student : studentManager.getAllStudents()) {
            studentComboBox.addItem(student);
            if (student.getStudentId().equals(selectedStudentId)) {
                studentComboBox.setSelectedItem(student);
            }
        }
        
        updateGradeTable();
    }
}

/**
 * Custom renderer for the grade column in the grade table
 */
class GradeCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                  boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (column == 3 && value != null) { // Grade column
            String grade = value.toString();
            if (grade.startsWith("A")) {
                c.setForeground(new Color(0, 128, 0)); // Green for A grades
            } else if (grade.startsWith("B")) {
                c.setForeground(new Color(0, 0, 200)); // Blue for B grades
            } else if (grade.startsWith("C")) {
                c.setForeground(new Color(200, 150, 0)); // Orange for C grades
            } else if (grade.startsWith("D")) {
                c.setForeground(new Color(200, 100, 0)); // Dark orange for D grades
            } else if (grade.equals("F")) {
                c.setForeground(new Color(200, 0, 0)); // Red for F grades
            } else {
                c.setForeground(Color.BLACK); // Default color
            }
            
            setHorizontalAlignment(JLabel.CENTER);
            setFont(getFont().deriveFont(Font.BOLD));
        } else {
            setHorizontalAlignment(JLabel.LEFT);
            setForeground(Color.BLACK);
            setFont(getFont().deriveFont(Font.PLAIN));
        }
        
        return c;
    }
}