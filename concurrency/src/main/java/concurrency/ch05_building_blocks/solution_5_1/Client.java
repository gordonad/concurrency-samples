package concurrency.ch05_building_blocks.solution_5_1;

import concurrency.ch05_building_blocks.exercise_5_1.Alert;
import concurrency.ch05_building_blocks.exercise_5_1.AlertLevel;

import java.util.Collection;

/**
 * We did not need to change the client.  Client-side locking would not work,
 * since we do not have a handle to the synchronized collection.  The safest
 * way of solving the ConcurrentModificationException is to either use a
 * thread-safe collection or to return copies of the synchronized collection.
 * In other words, solve it inside the AlertProvider class, rather than here.
 * Also, the ConcurrentModificationException occurs during the for() loop, not
 * where the comment indicated.  However, when my client sent me the code, he
 * thought it was occuring in the getAlerts() method.
 */
public class Client {
    private final AlertProvider alertProvider = AlertProvider.getInstance();

    public void checkAlerts() {
        Collection<Alert> alerts = alertProvider.getAlerts(); // <---I get a ConcurrentModificationException here
        for (Alert alert : alerts) {
            if (alert.getLevel() != AlertLevel.GREEN) {
                System.out.println("Alert level " + alert.getLevel());
            }
        }
    }
}
