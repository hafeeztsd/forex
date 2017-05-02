package org.progresssoft.forex.service;

import java.io.InputStream;

import org.progresssoft.forex.exception.ForexException;
import org.progresssoft.forex.model.Deal;

/**
 * Deal service perform business logic related to {@link Deal}
 * 
 * @author hafeeztsd
 *
 */
public interface DealService {

	/**
	 * Load the given file to Database.
	 * 
	 * @param is
	 * @param source
	 * @throws ForexException
	 */
	void loadDeals(InputStream is,  String source) throws ForexException;
}
