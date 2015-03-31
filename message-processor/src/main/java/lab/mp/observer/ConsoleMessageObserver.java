package lab.mp.observer;

import lab.mp.model.Conversion;

import java.util.List;

/**
 * Observer that is capable of sending reports to console.
 */
public class ConsoleMessageObserver implements MessageObserver {
    @Override
    public void report(List<Conversion> conversions) {

        for (Conversion conversion : conversions) {
            System.out.println(conversion);
        }
        System.out.println("-------------------------------------------");
    }
}
