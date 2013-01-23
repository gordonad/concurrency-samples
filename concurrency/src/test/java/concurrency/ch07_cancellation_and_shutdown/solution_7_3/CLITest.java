package concurrency.ch07_cancellation_and_shutdown.solution_7_3;

import java.util.concurrent.*;

public class CLITest {
    private final int tries;
    private final int timeout;

    public CLITest(int tries, int timeout) {
        this.tries = tries;
        this.timeout = timeout;
    }

    public void run() throws InterruptedException {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        String input = null;
        // start working
        for (int i = 0; i < tries; i++) {
            System.out.println(String.valueOf(i + 1) + ". loop");
            Future<String> result = ex.submit(new ConsoleInputReadTask());
            try {
                input = result.get(timeout, TimeUnit.SECONDS);
                break;
            } catch (ExecutionException e) {
                e.getCause().printStackTrace();
            } catch (TimeoutException e) {
                System.out.println("Cancelling reading task");
                result.cancel(true);
                System.out.println("\nThread cancelled. input is now: null");
            }
        }
        System.out.println("Done. Your input was: " + input);
        ex.shutdownNow();
    }

    public static void main(String[] args) throws InterruptedException {
        int tries;
        int timeout;

        try {
            tries = Integer.parseInt(args[0]);
            timeout = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println(
                    "Usage: java CLITest [number of tries] [timeout in seconds]");
            System.out.println("Defaulting to 3 tries of 5 seconds each");
            tries = 5;
            timeout = 5;
        }

        new CLITest(tries, timeout).run();
    }
}