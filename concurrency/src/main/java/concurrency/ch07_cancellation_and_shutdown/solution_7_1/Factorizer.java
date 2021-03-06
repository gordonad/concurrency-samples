package concurrency.ch07_cancellation_and_shutdown.solution_7_1;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Our solution checks the Thread.interrupted() flag and throws an exception
 * if that returns true.  We could also check the flag less frequently if we
 * wanted to minimize the cost of reading the flag value.
 */
public class Factorizer {
    public long[] factor(long number) throws InterruptedException {
        Collection<Long> factors = new ArrayList<>();
        for (long factor = 2; factor <= number; factor++) {
            while (number % factor == 0) {
                factors.add(factor);
                number /= factor;
            }
            if (Thread.interrupted()) throw new InterruptedException();
        }
        long[] result = new long[factors.size()];
        int pos = 0;
        for (Long factor : factors) {
            result[pos++] = factor;
        }
        return result;
    }
}