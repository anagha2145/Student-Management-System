public class Student extends Person {
    // Student class: stores student details and extends the abstract Person class
    private static int totalStudents = 0;
    private int id;
    private String course;
    private double marks;

    public Student(int id, String name, String course, int age, double marks) {
        super(name, age);
        this.id = id;
        this.course = course;
        this.marks = marks;
        totalStudents++;
    }

    public int getId() { return id; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }

    public static int getTotalStudents() { return totalStudents; }
    public static void decreaseCount() { totalStudents--; }

    public String getGrade() {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 40) return "D";
        else return "F";
    }
    @Override
    public String getRole() {
        return "Student";
    }

    // Format used to store the student in the text file
    public String toFileString() {
        return id + "," + getName() + "," + course + "," + getAge() + "," + marks;
    }

    @Override
    public String toString() {
        return String.format("%-6d %-15s %-18s %-5d %-8.1f %s",
                id, getName(), course, getAge(), marks, getGrade());
    }
}