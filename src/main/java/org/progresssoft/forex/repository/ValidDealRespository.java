package org.progresssoft.forex.repository;

import java.util.List;

import org.progresssoft.forex.model.ValidDeal;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Deal repository to interact with mongo db for all kind {@link ValidDeal} related
 * operations.
 * 
 * @author hafeeztsd
 *
 */
public interface ValidDealRespository extends MongoRepository<ValidDeal, String> {

	public List<ValidDeal> findByFileName(String fileName);

	public List<ValidDeal> deleteByFileName(String fileName);

}
