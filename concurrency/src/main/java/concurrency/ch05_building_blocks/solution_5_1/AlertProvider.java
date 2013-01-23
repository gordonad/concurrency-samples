package concurrency.ch05_building_blocks.solution_5_1;

import concurrency.ch05_building_blocks.exercise_5_1.Alert;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The reason we had a problem in the old AlertProvider was that the "alerts"
 * List changed whilst we were iterating, causing an error in the getAlerts()
 * method.  We can solve that by either making a copy of the collection whenever
 * we return the collection of alerts, or by using something like a
 * CopyOnWriteArrayList.  We could also use a ConcurrentLinkedQueue.
 */
public class AlertProvider {
    private final List<Alert> alerts = new CopyOnWriteArrayList<>();

    public Collection<Alert> getAlerts() {
        return Collections.unmodifiableCollection(alerts);
    }

    private final static AlertProvider instance = new AlertProvider();

    public static AlertProvider getInstance() {
        return instance;
    }

    public boolean addAlert(Alert alert) {
        return alerts.add(alert);
    }

    public boolean removeAlert(Alert alert) {
        return alerts.remove(alert);
    }
}