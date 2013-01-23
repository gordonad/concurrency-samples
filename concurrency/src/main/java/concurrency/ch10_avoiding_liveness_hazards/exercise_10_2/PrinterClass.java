package concurrency.ch10_avoiding_liveness_hazards.exercise_10_2;

/**
 * TODO: Run this class and determine if there are any liveness hazards.  If
 * TODO: there are, prove them in a unit test and then remove them, making sure
 * TODO: the unit test now passes.
 */
public class PrinterClass {
    private static final boolean OUTPUT_TO_SCREEN = false;
    private boolean printingEnabled = OUTPUT_TO_SCREEN;

    private synchronized static void print(PrinterClass pc,
                                           String s) {
        if (pc.isPrintingEnabled()) {
            System.out.println("Printing: " + s);
        }
    }

    public void print(String s) {
        print(this, s);
    }

    public synchronized boolean isPrintingEnabled() {
        return printingEnabled;
    }

    public synchronized void setPrintingEnabled(
            boolean printingEnabled) {
        if (!printingEnabled) {
            print(this, "Printing turned off!");
        }
        this.printingEnabled = printingEnabled;
    }
}