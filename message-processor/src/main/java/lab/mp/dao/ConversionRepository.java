package lab.mp.dao;

import lab.mp.model.Conversion;

import java.util.List;


/**
 * Repository for conversions.
 */
public interface ConversionRepository {

    /**
     * Save conversion.
     * @param conversion conversion to be saved.
     */
    void save(Conversion conversion);

    /**
     * Finds conversion for given parameters.
     * @param currencyFrom conversion from which currency
     * @param currencyTo conversion to which currency
     * @return conversion if found; <code>null</code> otherwise.
     */
    Conversion find(String currencyFrom, String currencyTo);

    /**
     * Get all conversions.
     * @return conversion if there are any; empty list otherwise.
     */
    List<Conversion> getConversions();
}
