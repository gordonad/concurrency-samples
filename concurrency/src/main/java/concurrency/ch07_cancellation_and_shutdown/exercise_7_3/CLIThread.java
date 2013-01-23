package concurrency.ch07_cancellation_and_shutdown.exercise_7_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLIThread extends Thread {
    String input;

    @Override
    public void run() {
        System.out.println("CLIThread run() called.");
        input = null;
        try {
            while (!Thread.currentThread().isInterrupted() && null ==
                    StringHolder.getInstance().getString()) {
                System.out.println("Please type something: ");
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                try {
                    input = br.readLine();
                    System.out.println("Thank You for providing input!");
                    StringHolder.getInstance().setString(input);
                    System.out.println("Saving '" + input + "' in StringHolder.");
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    input = null;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("finally ...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            input = null;
        } finally {
            System.out.println("finally ... exit");
        }
    }

    public void cancel() {
        System.out.println("CLIThread cancel() called.");
        interrupt();
    }
}