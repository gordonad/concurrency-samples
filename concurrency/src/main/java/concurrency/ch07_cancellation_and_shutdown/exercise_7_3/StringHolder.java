package concurrency.ch07_cancellation_and_shutdown.exercise_7_3;

public final class StringHolder {
    private static StringHolder inst;

    public static synchronized StringHolder getInstance() {
        if (null == inst) {
            inst = new StringHolder();
        }
        return inst;
    }

    private String s;

    public String getString() {
        return s;
    }

    public void setString(String arg) {
        s = arg;
    }
}