import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a whole number.");
                sc.nextLine();
            }
        }
    }
    static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double value = sc.nextDouble();
                sc.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }
    }

    static String readText(String message) {
        String text;
        do {
            System.out.print(message);
            text = sc.nextLine().trim().replace(",", " ");
            if (text.isEmpty()) {
                System.out.println("Error: This field cannot be empty.");
            }
        } while (text.isEmpty());
        return text;
    }
    static void printMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("===== Student Management System =====\n");
        menu.append("1. Add Student\n");
        menu.append("2. View All Students\n");
        menu.append("3. Search Student\n");
        menu.append("4. Update Student\n");
        menu.append("5. Delete Student\n");
        menu.append("6. Display Unique Courses\n");
        menu.append("7. Exit\n");
        System.out.print(menu);
    }

    static void addStudent(StudentManager manager) {
        int id = readInt("Enter Student ID: ");
        if (id <= 0) {
            System.out.println("Error: Invalid Student ID. ID must be a positive number.");
            return;
        }
        if (manager.exists(id)) {
            System.out.println("Error: Student ID " + id + " already exists.");
            return;
        }
        String name = readText("Enter Name: ");
        String course = readText("Enter Course: ");
        int age = readInt("Enter Age: ");
        if (age <= 0 || age > 100) {
            System.out.println("Error: Invalid age. Student not added.");
            return;
        }
        double marks = readDouble("Enter Marks (0-100): ");
        if (marks < 0 || marks > 100) {
            System.out.println("Error: Marks must be between 0 and 100. Student not added.");
            return;
        }
        manager.addRecord(new Student(id, name, course, age, marks));
    }

    static void searchStudent(StudentManager manager) {
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        int option = readInt("Enter option: ");
        if (option == 1) {
            manager.searchRecord(readInt("Enter Student ID: "));
        } else if (option == 2) {
            manager.searchRecord(readText("Enter Student Name: "));
        } else {
            System.out.println("Error: Invalid option.");
        }
    }

    static void updateStudent(StudentManager manager) {
        int id = readInt("Enter Student ID to update: ");
        if (!manager.exists(id)) {
            System.out.println("Error: No student found with ID " + id + ".");
            return;
        }
        String name = readText("Enter new Name: ");
        String course = readText("Enter new Course: ");
        int age = readInt("Enter new Age: ");
        double marks = readDouble("Enter new Marks (0-100): ");
        if (age <= 0 || age > 100 || marks < 0 || marks > 100) {
            System.out.println("Error: Invalid age or marks. Update cancelled.");
            return;
        }
        manager.updateRecord(id, name, course, age, marks);
    }

    static void exitProgram(StudentManager manager) {
        System.out.println("Saving backup, please wait...");
        BackupThread backup = new BackupThread(manager.getAllStudents(), "students_backup.txt");
        backup.start();
        try {
            backup.join();   // main thread waits until the backup thread finishes
        } catch (InterruptedException e) {
            System.out.println("Error: Backup was interrupted.");
        }
        System.out.println("Thank you for using the Student Management System. Goodbye!");
    }

    public static void main(String[] args) {
        StudentManager manager = new StudentManager("students.txt");
        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: addStudent(manager); break;
                case 2: manager.viewAllRecords(); break;
                case 3: searchStudent(manager); break;
                case 4: updateStudent(manager); break;
                case 5: manager.deleteRecord(readInt("Enter Student ID to delete: ")); break;
                case 6: manager.displayUniqueCourses(); break;
                case 7: exitProgram(manager); break;
                default: System.out.println("Error: Invalid menu choice. Please enter a number from 1 to 7.");
            }
            System.out.println();
        } while (choice != 7);
        sc.close();
    }
}