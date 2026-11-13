import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
public class StudentManager implements RecordOperations {
    private HashMap<Integer, Student> students = new HashMap<>();   // ID -> Student
    private String fileName;
    public StudentManager(String fileName) {
        this.fileName = fileName;
        // Load saved students from the file when the program starts
        for (Student s : FileHandler.loadStudents(fileName)) {
            students.put(s.getId(), s);
        }
    }
    public boolean exists(int id) {
        return students.containsKey(id);
    }
    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
    private void save() {
        FileHandler.saveStudents(fileName, getAllStudents());
    }
    @Override
    public void addRecord(Student student) {
        if (students.containsKey(student.getId())) {
            System.out.println("Error: Student ID " + student.getId() + " already exists.");
            Student.decreaseCount();
            return;
        }
        students.put(student.getId(), student);
        save();
        System.out.println("Student added successfully.");
    }

    @Override
    public void viewAllRecords() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        ArrayList<Student> list = getAllStudents();
        list.sort((a, b) -> Integer.compare(a.getId(), b.getId()));

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-6s %-15s %-18s %-5s %-8s %s%n",
                "ID", "Name", "Course", "Age", "Marks", "Grade"));
        sb.append("-----------------------------------------------------------------\n");
        for (Student s : list) {
            sb.append(s).append("\n");
        }
        System.out.print(sb);
        System.out.println("Total students: " + Student.getTotalStudents());
    }

    @Override
    public void searchRecord(int id) {
        Student s = students.get(id);
        if (s == null) {
            System.out.println("Student with ID " + id + " not found.");
        } else {
            printStudent(s);
        }
    }

    @Override
    public void searchRecord(String name) {
        ArrayList<Student> matches = new ArrayList<>();
        for (Student s : students.values()) {
            if (s.getName().toLowerCase().contains(name.toLowerCase())) {
                matches.add(s);
            }
        }
        if (matches.isEmpty()) {
            System.out.println("No student found with the name \"" + name + "\".");
        } else {
            for (Student s : matches) {
                printStudent(s);
            }
        }
    }

    private void printStudent(Student s) {
        StringBuilder sb = new StringBuilder();
        sb.append("Student Found\n");
        sb.append("ID     : ").append(s.getId()).append("\n");
        sb.append("Name   : ").append(s.getName()).append("\n");
        sb.append("Course : ").append(s.getCourse()).append("\n");
        sb.append("Age    : ").append(s.getAge()).append("\n");
        sb.append("Marks  : ").append(s.getMarks()).append(" (Grade ").append(s.getGrade()).append(")\n");
        System.out.print(sb);
    }

    @Override
    public void updateRecord(int id, String name, String course, int age, double marks) {
        Student s = students.get(id);
        if (s == null) {
            System.out.println("Error: No student found with ID " + id + ".");
            return;
        }
        s.setName(name);
        s.setCourse(course);
        s.setAge(age);
        s.setMarks(marks);
        save();
        System.out.println("Student record updated successfully.");
    }

    @Override
    public void deleteRecord(int id) {
        if (students.remove(id) != null) {
            Student.decreaseCount();
            save();
            System.out.println("Student with ID " + id + " removed successfully.");
        } else {
            System.out.println("Error: No student found with ID " + id + ".");
        }
    }

    public void displayUniqueCourses() {
        if (students.isEmpty()) {
            System.out.println("No courses available. Please add students first.");
            return;
        }
        HashSet<String> courses = new HashSet<>();   // HashSet keeps each course once
        for (Student s : students.values()) {
            courses.add(s.getCourse().toUpperCase());
        }
        System.out.println("Unique Courses:");
        for (String course : courses) {
            System.out.println("- " + course);
        }
    }
}