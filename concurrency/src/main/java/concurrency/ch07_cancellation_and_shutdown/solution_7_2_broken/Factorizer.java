package concurrency.ch07_cancellation_and_shutdown.solution_7_2_broken;

import java.util.ArrayList;
import java.util.Collection;

public class Factorizer {
    public long[] factor(long number) {
        Collection<Long> factors = new ArrayList<>();
        for (long factor = 2; factor <= number; factor++) {
            while (number % factor == 0) {
                factors.add(factor);
                number /= factor;
            }
        }
        long[] result = new long[factors.size()];
        int pos = 0;
        for (Long factor : factors) {
            result[pos++] = factor;
        }
        return result;
    }
}