package org.progresssoft.forex.repository;

import org.progresssoft.forex.model.CurrencyFrequency;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Deal repository to interact with mongo db for all kind
 * {@link CurrencyFrequency} related operations.
 * 
 * @author hafeeztsd
 *
 */
public interface CurrencyFrequencyRespository extends MongoRepository<CurrencyFrequency, String> {

}
