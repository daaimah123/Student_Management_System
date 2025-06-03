# 🎓 University Management System 🎓

## Hey 👋🏾

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

#### What You'll Need First

* **Java JDK 11+** - Because we're not savages using Java 8 anymore
* **Maven** - Trust me, it's worth learning
* **VS Code** with Java extensions - Or use IntelliJ if you're fancy

#### Quick Start Guide

1. **Clone this bad boy**
   ```bash
   git clone git@github.com:daaimah123/Student_Management_System.git
   cd UniversityManagementSystem
   ```
2. **Build it with Maven**

```shellscript
mvn clean install
```

(This might take a minute the first time as Maven downloads half the internet)

3. **Fire it up!**

- Find `UniversityManagementApp.java` in VS Code
- Click the little play button next to the main method
- Or run it from the command line like a hacker:

```shellscript
java -jar target/UniversityManagementSystem-1.0-SNAPSHOT.jar
```

4. **Database Magic** ✨
   The app will create a `university.db` file the first time you run it. No setup needed! I spent way too long making this work automatically.

## If Something Breaks 💔

- **"mvn not found"** - You forgot to install Maven or add it to your PATH. We've all been there.
- **Database errors** - Check if your `university.db` file is there. If it is, maybe delete it and let the app create a fresh one.
- **GUI looks weird** - Welcome to the wonderful world of Swing! It's... special.
- **Other random errors** - Have you tried turning it off and on again? No, seriously, sometimes that works.

## Project Structure 👷🏾‍♀️

I tried to keep things organized, but you know how it goes... Here's the basic layout:

```
UniversityManagementSystem/
├── pom.xml                   # Maven config (don't touch unless you know what you're doing)
├── university.db             # Your database (appears after first run)
└── src/
└── main/
└── java/
└── com/
└── university/
├── main/         # Where the magic starts
├── model/        # All the data objects
├── dao/          # Database stuff
├── service/      # Business logic
├── gui/          # Pretty UI things
└── util/         # Random helper stuff
```

## Future Plans (or "Things I'll Probably Never Get Around To") 🔮

- Dark mode - Because my eyes hurt at night
- Reports and analytics - For when the dean wants fancy charts
- Calendar integration - To remind students about deadlines they'll ignore anyway
- Mobile app - Just kidding, I'm not that ambitious

## Final Thoughts

This was a fun project to build! If you find it useful, great! If you find bugs, well... they're not bugs, they're "surprise features." 😉

Happy university managing!
