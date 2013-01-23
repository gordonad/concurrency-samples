package concurrency.ch05_building_blocks.exercise_5_1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * TODO: We need to avoid the ConcurrentModification exception in Client class.
 *
 * @see Client
 */
public class AlertProvider {
    private final List<Alert> alerts = Collections.synchronizedList(
            new ArrayList<Alert>(10));

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
