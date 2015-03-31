package lab.mp.observer;

import lab.mp.model.Conversion;

import java.util.List;

/**
 * Observer for processed messages.
 */
public interface MessageObserver {

    /**
     * Reports conversion that happened.
     * @param conversions all conversions that happened.
     */
    void report(List<Conversion> conversions);
}
