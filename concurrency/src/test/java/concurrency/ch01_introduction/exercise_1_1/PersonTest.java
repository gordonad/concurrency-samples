package concurrency.ch01_introduction.exercise_1_1;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;
import org.junit.Test;

import static concurrency.util.TestHelpers.assertTypeIsAnnotated;

/**
 * DO NOT CHANGE.
 */
public class PersonTest {
    @Test
    public void testPerson1() {
        assertTypeIsAnnotated(Immutable.class, Person1.class);
    }

    @Test
    public void testPerson2() {
        assertTypeIsAnnotated(NotThreadSafe.class, Person2.class);
    }

    @Test
    public void testPerson3() {
        assertTypeIsAnnotated(ThreadSafe.class, Person3.class);
    }
}