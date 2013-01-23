package concurrency.ch05_building_blocks.exercise_5_1;

import java.util.Collection;

/**
 * TODO: We need to avoid the ConcurrentModification exception.
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
