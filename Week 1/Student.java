import java.util.List;

public class Student {

    private String forename;
    private String surname;

    private int age;
    private String courseName;
    private List<Module> modulesEnrolled;

    public Student(String forename, String surname, int age, String courseName) {
        this.forename = forename;
        this.surname = surname;
        this.age = age;
        this.courseName = courseName;
    }

    public void enroll(Module module) {
        modulesEnrolled.add(module);
        System.out.printf("This student is now enroleld in: ");
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
