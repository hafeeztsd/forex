package org.progresssoft.forex.repository;

import org.progresssoft.forex.model.Deal;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Deal repository to interact with mongo db for all kind {@link Deal} related
 * operations.
 * 
 * @author hafeeztsd
 *
 */
public interface DealRespository extends MongoRepository<Deal, String> {

}
