package org.progresssoft.forex.repository;

import org.progresssoft.forex.model.DealSource;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Deal repository to interact with mongo db for all kind {@link DealSource} related
 * operations.
 * 
 * @author hafeeztsd
 *
 */
public interface DealSourceRespository extends MongoRepository<DealSource, String> {

}
