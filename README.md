# 🎓 University Management System 🎓

## Hey there! 👋

Welcome to my University Management System! This little project started as a way to save my sanity after watching university staff struggle with spreadsheets and paper forms for the millionth time. I figured there had to be a better way!

## What Does This Thing Do? 🤔

This desktop app lets you manage all the university stuff without losing your mind:

* 👨‍🎓 **Students** - Add those eager learners, track their info, and stop losing their paperwork
* 👩‍🏫 **Employees** - Keep tabs on professors and staff (and maybe figure out who keeps eating everyone's lunch in the break room)
* 📚 **Courses** - Organize what's being taught without the usual scheduling nightmare
* 🏢 **Departments** - Because someone needs to know which department is hogging the budget
* ✏️ **Enrollments** - Match students to courses without the usual registration day chaos
* 📊 **Grades** - Record those A's (and occasional F's) without the end-of-semester panic

All wrapped up in a (hopefully) intuitive Java Swing interface that won't make your eyes bleed!

## The Tech Stuff (For My Fellow Nerds) 🤓

I built this with:
* **Java** - Because I'm a glutton for semicolons
* **Swing** - Old school UI that still gets the job done
* **SQLite** - Lightweight database that doesn't need a server (perfect for my lazy self)
* **Maven** - Because manually managing dependencies is for masochists

The architecture follows a layered approach that I'm actually pretty proud of:
1. **GUI Layer** - All the pretty buttons and tables
2. **Service Layer** - Where the business logic lives
3. **DAO Layer** - Database stuff that I'll probably regret if we ever switch databases
4. **Model Layer** - Plain old Java objects that don't do much but are essential

## How to Get This Running 🏃‍♂️

### What You'll Need First
* **Java JDK 11+** - Because we're not savages using Java 8 anymore
* **Maven** - Trust me, it's worth learning
* **VS Code** with Java extensions - Or use IntelliJ if you're fancy

### Quick Start Guide

1. **Clone this bad boy**
   \`\`\`bash
   git clone <wherever-you-put-this-repo>
   cd UniversityManagementSystem
   \`\`\`

2. **Build it**
   \`\`\`bash
   mvn clean compile
   \`\`\`

3. **Run it**
   \`\`\`bash
   mvn exec:java -Dexec.mainClass="com.university.main.UniversityManagementApp"
   \`\`\`

4. **Or create a runnable JAR**
   \`\`\`bash
   mvn clean package
   java -jar target/UniversityManagementSystem-1.0-SNAPSHOT.jar
   \`\`\`

## Current Status & TODOs 📝

### ✅ What's Working
- Student management (add, edit, delete, view)
- Department management (add, edit, delete, view)
- Database initialization and basic CRUD operations
- Clean layered architecture with proper separation of concerns

### 🚧 What's In Progress
The system has a solid foundation, but there are quite a few TODOs scattered throughout the code that I'm planning to tackle:

#### Missing GUI Components
- Employee management panel and dialog
- Course management panel and dialog  
- Enrollment management panel and dialog
- Grade management panel and dialog

#### Data Validation & Handling
- Consistent date handling (currently mixing String and LocalDate)
- Robust email validation
- Date format validation with proper error messages
- Input field length limits and character restrictions
- Duplicate prevention (emails, course codes, department names)

#### User Experience Improvements
- Search and filter functionality for all tables
- Table sorting capabilities
- Pagination for large datasets
- Keyboard shortcuts and better navigation
- Right-click context menus
- Export functionality (CSV, PDF)
- Date picker components instead of text fields

#### Business Logic Enhancements
- Enrollment capacity checks for courses
- Prerequisite validation for course enrollment
- Cascade handling for deletions (what happens to enrollments when a student is deleted?)
- Department dependency checks before deletion
- Grade management tied to enrollments

#### Technical Improvements
- Database indexing for better performance
- Connection pooling
- Proper logging framework instead of printStackTrace()
- Configuration management
- Better error handling with specific exception types

### 🎯 Next Steps
1. **Complete the GUI** - Finish the missing panels and dialogs
2. **Improve validation** - Add comprehensive input validation throughout
3. **Enhance UX** - Add search, sorting, and better navigation
4. **Business rules** - Implement proper cascade handling and business logic
5. **Polish** - Add icons, better styling, and keyboard shortcuts

## Database Schema 🗄️

The SQLite database gets created automatically with these tables:
- `departments` - University departments
- `students` - Student information
- `employees` - Faculty and staff
- `courses` - Course catalog
- `enrollments` - Student-course relationships
- `grades` - Student grades for enrollments

Foreign keys are properly set up to maintain data integrity, and I've got cascading deletes where it makes sense.

## Contributing 🤝

Found a bug? Want to add a feature? Have a better way to do something? 

1. Fork it
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Just remember to check the TODOs in the code - there might already be a plan for what you're thinking of!

## License 📄

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments 🙏

- Coffee ☕ - For making this possible
- Stack Overflow - For answering questions I didn't know I had
- My patience - For not giving up when Swing decided to be... Swing

---

*Built with ❤️ and a healthy dose of frustration with existing university management systems*
