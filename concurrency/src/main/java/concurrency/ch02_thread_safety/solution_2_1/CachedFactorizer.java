package concurrency.ch02_thread_safety.solution_2_1;

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
 * In our solution we synchronize the methods getHits() and getCacheHitRatio().
 */
@ThreadSafe
public class CachedFactorizer extends HttpServlet {
    @GuardedBy("this")
    private BigInteger lastNumber;
    @GuardedBy("this")
    private BigInteger[] lastFactors;
    @GuardedBy("this")
    private long hits;
    @GuardedBy("this")
    private long cacheHits;

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
            throws ServletException, IOException {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = null;
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
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