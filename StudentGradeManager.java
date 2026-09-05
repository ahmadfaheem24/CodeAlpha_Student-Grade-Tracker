import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Student Grade Management System
 * ---------------------------------
 * Console-based Java program that demonstrates core OOP + collection
 * concepts: classes, encapsulation, ArrayList, loops, and menu-driven I/O.
 */
public class StudentGradeManager {

    // ---- Student model -------------------------------------------------
    static class Student {
        private final String rollNo;
        private final String name;
        private final List<Double> grades = new ArrayList<>();

        Student(String rollNo, String name) {
            this.rollNo = rollNo;
            this.name = name;
        }

        void addGrade(double grade) {
            grades.add(grade);
        }

        double getAverage() {
            if (grades.isEmpty()) return 0;
            double sum = 0;
            for (double g : grades) sum += g;
            return sum / grades.size();
        }

        double getHighest() {
            double max = grades.get(0);
            for (double g : grades) if (g > max) max = g;
            return max;
        }

        double getLowest() {
            double min = grades.get(0);
            for (double g : grades) if (g < min) min = g;
            return min;
        }

        char getLetterGrade() {
            double avg = getAverage();
            if (avg >= 90) return 'A';
            if (avg >= 80) return 'B';
            if (avg >= 70) return 'C';
            if (avg >= 60) return 'D';
            return 'F';
        }

        String getRollNo() { return rollNo; }
        String getName() { return name; }
        List<Double> getGrades() { return grades; }
    }

    // ---- Program state ---------------------------------------------------
    private static final List<Student> students = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> displaySummaryReport();
                case 3 -> displayClassStatistics();
                case 4 -> {
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== Student Grade Management System =====");
        System.out.println("1. Add Student & Grades");
        System.out.println("2. Display Summary Report");
        System.out.println("3. Display Class Statistics");
        System.out.println("4. Exit");
    }

    private static void addStudent() {
        System.out.print("Enter Roll No: ");
        String rollNo = scanner.next();
        scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        Student student = new Student(rollNo, name);
        int numGrades = readInt("How many subjects/grades to enter? ");

        for (int i = 1; i <= numGrades; i++) {
            double grade = readDouble("Enter grade #" + i + ": ");
            student.addGrade(grade);
        }

        students.add(student);
        System.out.println("Student added successfully!\n");
    }

    private static void displaySummaryReport() {
        if (students.isEmpty()) {
            System.out.println("No student records found.\n");
            return;
        }

        System.out.println("\n==================== SUMMARY REPORT ====================");
        System.out.printf("%-8s %-15s %-8s %-8s %-8s %-6s%n",
                "Roll", "Name", "Average", "Highest", "Lowest", "Grade");
        System.out.println("---------------------------------------------------------");

        for (Student s : students) {
            System.out.printf("%-8s %-15s %-8.2f %-8.2f %-8.2f %-6s%n",
                    s.getRollNo(), s.getName(), s.getAverage(),
                    s.getHighest(), s.getLowest(), s.getLetterGrade());
        }
        System.out.println("==========================================================\n");
    }

    private static void displayClassStatistics() {
        if (students.isEmpty()) {
            System.out.println("No student records found.\n");
            return;
        }

        double classSum = 0;
        double classHighest = Double.MIN_VALUE;
        double classLowest = Double.MAX_VALUE;
        Student topStudent = students.get(0);
        Student bottomStudent = students.get(0);

        for (Student s : students) {
            double avg = s.getAverage();
            classSum += avg;

            if (s.getHighest() > classHighest) classHighest = s.getHighest();
            if (s.getLowest() < classLowest) classLowest = s.getLowest();
            if (avg > topStudent.getAverage()) topStudent = s;
            if (avg < bottomStudent.getAverage()) bottomStudent = s;
        }

        double classAverage = classSum / students.size();

        System.out.println("\n================ CLASS STATISTICS ================");
        System.out.printf("Total Students   : %d%n", students.size());
        System.out.printf("Class Average    : %.2f%n", classAverage);
        System.out.printf("Highest Score    : %.2f%n", classHighest);
        System.out.printf("Lowest Score     : %.2f%n", classLowest);
        System.out.printf("Top Performer    : %s (%.2f avg)%n", topStudent.getName(), topStudent.getAverage());
        System.out.printf("Needs Improvement: %s (%.2f avg)%n", bottomStudent.getName(), bottomStudent.getAverage());
        System.out.println("===================================================\n");
    }

    // ---- Input helpers ---------------------------------------------------
    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
