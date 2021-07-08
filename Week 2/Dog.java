
/**
 * Exploring Encapsulation: Comparing and Modifying Objects.
 * */
public class Dog {

    String name;
    private int age;
    String breed;

    public Dog() {

    }

    public Dog(String name, int age, String breed) {
        this.name = name;

        if (age < 0) {
            throw new IllegalArgumentException("Age is less than 0.");
        }

        this.age = age;
        this.breed = breed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        if (!(age < this.age)) {
            return;
        }

        this.age = age;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }






}
