import java.util.ArrayList;

public class BackupThread extends Thread {
    private ArrayList<Student> students;
    private String backupFile;

    public BackupThread(ArrayList<Student> students, String backupFile) {
        this.students = students;
        this.backupFile = backupFile;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);   // small pause to show the thread working
        } catch (InterruptedException e) {
            System.out.println("Backup was interrupted.");
            return;
        }
        FileHandler.saveStudents(backupFile, students);
        System.out.println("Backup completed: " + backupFile);
    }
}