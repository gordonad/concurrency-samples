package concurrency.ch01_introduction.exercise_1_1;

/**
 * TODO: Annotate this class
 * The class should be annotated with either @ThreadSafe, @NotThreadSafe or
 * the @Immutable tag.
 */
public class Person1 {
    private final String firstName;
    private final String surname;
    private final int age;

    public Person1(String firstName,
                   String surname, int age) {
        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
    }
}