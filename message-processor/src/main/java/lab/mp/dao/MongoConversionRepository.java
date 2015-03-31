package lab.mp.dao;

import lab.mp.model.Conversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Mongo repository for conversions.
 */
@Repository
public class MongoConversionRepository implements ConversionRepository{

    @Override
    public void save(Conversion conversion) {
        conversionRepo.save(conversion);
    }

    @Override
    public Conversion find(String currencyFrom, String currencyTo) {
        return conversionRepo.find(currencyFrom, currencyTo);
    }

    @Override
    public List<Conversion> getConversions() {
        return conversionRepo.findAll();
    }

    @Autowired
    private ConversionRepo conversionRepo;
}

interface ConversionRepo extends MongoRepository<Conversion, String> {

    @Query(value="{ 'from' : ?0, 'to' : ?1 }")
    Conversion find(String currencyFrom, String currencyTo);
}