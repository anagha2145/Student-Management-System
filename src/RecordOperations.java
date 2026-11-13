// Interface: lists the operations every record manager must provide
public interface RecordOperations {
    void addRecord(Student student);
    void viewAllRecords();
    void searchRecord(int id);
    void searchRecord(String name);   // method overloading
    void updateRecord(int id, String name, String course, int age, double marks);
    void deleteRecord(int id);
}