import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileHandler {
    public static void saveStudents(String fileName, ArrayList<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Student s : students) {
                writer.println(s.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error while saving data: " + e.getMessage());
        }
    }
    public static ArrayList<Student> loadStudents(String fileName) {
        ArrayList<Student> list = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            return list;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 5) {
                    try {
                        list.add(new Student(Integer.parseInt(p[0].trim()), p[1].trim(),
                                p[2].trim(), Integer.parseInt(p[3].trim()),
                                Double.parseDouble(p[4].trim())));
                    } catch (NumberFormatException e) {
                        System.out.println("Skipped a corrupted line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading data: " + e.getMessage());
        }
        return list;
    }
}