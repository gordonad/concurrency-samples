package concurrency.ch02_thread_safety.exercise_2_1;

import concurrency.math.Factorizer;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * TODO: Add counters in a thread-safe way
 * Add a counter for the number of hits and the number of cache hits.  Return
 * these values from the methods getHits() and getCacheHitRatio().  The class
 * should still be thread-safe when you are done.
 */
@ThreadSafe
public class CachedFactorizer extends HttpServlet {
    @GuardedBy("this")
    private BigInteger lastNumber;
    @GuardedBy("this")
    private BigInteger[] lastFactors;

    public long getHits() {
        throw new UnsupportedOperationException("todo");
    }

    public double getCacheHitRatio() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
            throws ServletException, IOException {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = null;
        synchronized (this) {
            if (i.equals(lastNumber)) factors = lastFactors.clone();
        }
        if (factors == null) {
            factors = Factorizer.factor(i);
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
        encodeIntoResponse(res, factors);
    }

    private BigInteger extractFromRequest(ServletRequest req) {
        String numberStr = req.getParameter("number");
        if (numberStr == null) return BigInteger.ZERO;
        return new BigInteger(numberStr);
    }

    private void encodeIntoResponse(ServletResponse res, BigInteger[] factors)
            throws IOException {
        res.getOutputStream().print(Arrays.toString(factors));
    }
}