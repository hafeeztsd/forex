package org.progresssoft.forex.repository;

import java.util.List;

import org.progresssoft.forex.model.InvalidDeal;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Deal repository to interact with mongo db for all kind {@link InvalidDeal}
 * related operations.
 * 
 * @author hafeeztsd
 *
 */
public interface InvalidDealRespository extends MongoRepository<InvalidDeal, String> {

	public List<InvalidDeal> findByFileName(String fileName);

	public List<InvalidDeal> deleteByFileName(String fileName);

}
