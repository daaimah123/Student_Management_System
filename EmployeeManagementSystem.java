import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Employee Management System demonstrating Function interface and Streams usage
 * for processing employee data from a CSV file with various operations including 
 * dynamic filtering, transformation, and aggregation.
 */
public class EmployeeManagementSystem {
    
    /**
     * Employee class representing individual employee data
     */
    public static class Employee {
        private String name;
        private int age;
        private String department;
        private double salary;
        
        public Employee(String name, int age, String department, double salary) {
            this.name = name;
            this.age = age;
            this.department = department;
            this.salary = salary;
        }
        
        // Getters
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }
        
        @Override
        public String toString() {
            return String.format("Employee{name='%s', age=%d, department='%s', salary=%.2f}", 
                               name, age, department, salary);
        }
    }
    
    /**
     * Function that maps an Employee object to a concatenated string of their name and department.
     */
    private static final Function<Employee, String> mapEmployeeToNameAndDepartmentString = 
        employee -> employee.getName() + " - " + employee.getDepartment();
    
    /**
     * Returns a Predicate for filtering employees whose age is above a given threshold.
     * @param threshold The age threshold.
     * @return A Predicate to filter employees older than the threshold.
     */
    private static Predicate<Employee> isAgeAboveThreshold(int threshold) {
        return employee -> employee.getAge() > threshold;
    }
    
    /**
     * Function that maps an Employee object to their salary as a Double.
     */
    private static final Function<Employee, Double> mapEmployeeToSalaryDouble = Employee::getSalary;
    
    /**
     * Function that maps an Employee object to a detailed info string including name, department, and salary.
     */
    private static final Function<Employee, String> mapEmployeeToDetailedInfoString = 
        employee -> String.format("%s - %s (Salary: $%.2f)", 
                                employee.getName(), 
                                employee.getDepartment(), 
                                employee.getSalary());
    
    /**
     * Reads employee data from a CSV file and stores it in a list.
     * Assumes CSV format: Name,Age,Department,Salary
     * @param filePath The path to the CSV file.
     * @return A list of Employee objects.
     */
    private static List<Employee> readEmployeesFromCSV(String filePath) {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            // Skip header line
            String line = br.readLine(); 
            
            while ((line = br.readLine()) != null) {
                String[] attributes = line.split(",");
                if (attributes.length == 4) {
                    try {
                        String name = attributes[0].trim();
                        int age = Integer.parseInt(attributes[1].trim());
                        String department = attributes[2].trim();
                        double salary = Double.parseDouble(attributes[3].trim());
                        employees.add(new Employee(name, age, department, salary));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping malformed line (number format error): " + line + " - " + e.getMessage());
                    }
                } else {
                     System.err.println("Skipping malformed line (incorrect number of attributes): " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            // Depending on requirements, might throw exception or return empty list
        }
        return employees;
    }
    
    /**
     * Processes employee data using Function interface and streams.
     * Demonstrates chaining of multiple stream operations with a dynamic age filter.
     * @param employees The list of employees to process.
     * @param ageFilterThreshold The age threshold for filtering employees.
     */
    private static void processEmployeeData(List<Employee> employees, int ageFilterThreshold) {
        if (employees.isEmpty()) {
            System.out.println("No employee data to process.");
            return;
        }
        System.out.println("=== Original Employee Dataset (from CSV) ===");
        employees.forEach(System.out::println);
        
        // Generate collection of concatenated strings using Function interface
        List<String> nameAndDepartmentList = employees.stream()
            .map(mapEmployeeToNameAndDepartmentString)
            .collect(Collectors.toList());
        
        System.out.println("\n=== Name and Department Concatenation ===");
        nameAndDepartmentList.forEach(System.out::println);
        
        // Calculate average salary using stream's built-in functions
        OptionalDouble averageSalary = employees.stream()
            .mapToDouble(Employee::getSalary) // Or use mapEmployeeToSalaryDouble.applyAsDouble if it were ToDoubleFunction
            .average();
        
        System.out.println("\n=== Average Salary Calculation ===");
        averageSalary.ifPresentOrElse(
            avg -> System.out.printf("Average Salary: $%.2f%n", avg),
            () -> System.out.println("Could not calculate average salary (no data or all salaries invalid).")
        );
        
        // Filter employees above the dynamic age threshold and process
        System.out.printf("\n=== Employees Above Age %d ===%n", ageFilterThreshold);
        List<String> filteredEmployeeInfo = employees.stream()
            .filter(isAgeAboveThreshold(ageFilterThreshold))
            .map(mapEmployeeToNameAndDepartmentString)
            .collect(Collectors.toList());
        
        if (filteredEmployeeInfo.isEmpty()) {
            System.out.printf("No employees found above age %d.%n", ageFilterThreshold);
        } else {
            filteredEmployeeInfo.forEach(System.out::println);
        }
        
        // Enhanced processing with chained operations using dynamic filter
        System.out.printf("\n=== Enhanced Analysis: Filtered Employees (Above Age %d) with Salary Info ===%n", ageFilterThreshold);
        List<String> enhancedFilteredList = employees.stream()
            .filter(isAgeAboveThreshold(ageFilterThreshold))
            .map(mapEmployeeToDetailedInfoString)
            .sorted()
            .collect(Collectors.toList());

        if (enhancedFilteredList.isEmpty()) {
            System.out.printf("No employees found for enhanced analysis above age %d.%n", ageFilterThreshold);
        } else {
            enhancedFilteredList.forEach(System.out::println);
        }
        
        performAdvancedAnalysis(employees, ageFilterThreshold);
    }
    
    /**
     * Performs advanced statistical analysis demonstrating additional features.
     * @param employees The list of employees.
     * @param ageFilterThreshold The age threshold used for some analyses.
     */
    private static void performAdvancedAnalysis(List<Employee> employees, int ageFilterThreshold) {
        if (employees.isEmpty()) return;

        System.out.println("\n=== Advanced Statistical Analysis ===");
        
        // Department-wise average salary using grouping and parallel processing
        Map<String, Double> departmentAverageSalary = employees.parallelStream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
        
        System.out.println("Department-wise Average Salary:");
        departmentAverageSalary.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .forEach(entry -> 
                System.out.printf("  %s: $%.2f%n", entry.getKey(), entry.getValue()));
        
        // Salary statistics using stream operations
        DoubleSummaryStatistics salaryStats = employees.stream()
            .mapToDouble(Employee::getSalary)
            .summaryStatistics();
        
        System.out.println("\nSalary Statistics:");
        System.out.printf("  Count: %d%n", salaryStats.getCount());
        System.out.printf("  Min: $%.2f%n", salaryStats.getMin());
        System.out.printf("  Max: $%.2f%n", salaryStats.getMax());
        System.out.printf("  Average: $%.2f%n", salaryStats.getAverage());
        System.out.printf("  Sum: $%.2f%n", salaryStats.getSum());
        
        // Top performers analysis using stream chaining
        System.out.println("\nTop 3 Highest Paid Employees:");
        employees.stream()
            .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
            .limit(3)
            .map(mapEmployeeToDetailedInfoString)
            .forEach(info -> System.out.println("  " + info));
        
        // Age distribution analysis (using a fixed categorization for simplicity here,
        // but could also be made dynamic)
        Map<String, Long> ageDistribution = employees.stream()
            .collect(Collectors.groupingBy(
                employee -> {
                    if (employee.getAge() <= 30) return "Young (≤30)";
                    if (employee.getAge() <= 40) return "Mid-career (31-40)";
                    return "Senior (>40)";
                },
                Collectors.counting()
            ));
        
        System.out.println("\nAge Distribution:");
        ageDistribution.forEach((category, count) -> 
            System.out.printf("  %s: %d employees%n", category, count));
    }
    
    /**
     * Demonstrates lazy evaluation and short-circuiting behavior of streams.
     * @param employees The list of employees.
     */
    private static void demonstrateStreamEfficiency(List<Employee> employees) {
        if (employees.isEmpty()) return;

        System.out.println("\n=== Stream Efficiency Demonstration ===");
        
        // Lazy evaluation example - operations are not executed until terminal operation
        System.out.println("Finding first high-salary employee in Engineering (salary > $80,000):");
        Optional<Employee> firstHighSalaryEngineer = employees.stream()
            .peek(emp -> System.out.println("  Processing (peek): " + emp.getName())) // peek shows elements as they are processed
            .filter(emp -> emp.getDepartment().equals("Engineering"))
            .peek(emp -> System.out.println("  Passed Engineering filter: " + emp.getName()))
            .filter(emp -> emp.getSalary() > 80000)
            .peek(emp -> System.out.println("  Passed Salary filter: " + emp.getName()))
            .findFirst(); // Short-circuiting terminal operation
        
        firstHighSalaryEngineer.ifPresentOrElse(
            emp -> System.out.println("  Found: " + mapEmployeeToDetailedInfoString.apply(emp)),
            () -> System.out.println("  No high-salary engineer found matching criteria.")
        );
        
        // Short-circuiting with anyMatch
        boolean hasHighEarner = employees.stream()
            .peek(emp -> System.out.println("  Checking for high earner (anyMatch): " + emp.getName()))
            .anyMatch(emp -> emp.getSalary() > 90000); // Short-circuiting
        
        System.out.println("\nHas employee earning over $90,000: " + hasHighEarner);
    }
    
    /**
     * Main method demonstrating complete employee management system.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Employee Management System");
        System.out.println("Demonstrating Function Interface and Streams Usage");
        System.out.println("=".repeat(60)); 

        String csvFilePath = "employees.csv"; // Path to the CSV file
        List<Employee> employees = readEmployeesFromCSV(csvFilePath);

        if (employees.isEmpty()) {
            System.out.println("Exiting program as no employee data could be loaded from: " + csvFilePath);
            return;
        }
        
        int dynamicAgeThreshold = 30; // This can be configured, e.g., from args or properties file
        System.out.println("Using dynamic age filter threshold: " + dynamicAgeThreshold);

        processEmployeeData(employees, dynamicAgeThreshold);
        demonstrateStreamEfficiency(employees);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Program execution completed.");
    }
}
