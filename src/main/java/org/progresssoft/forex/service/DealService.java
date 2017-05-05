package org.progresssoft.forex.service;

import java.io.InputStream;
import java.util.List;

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
	boolean loadDeals(InputStream is,  String source) throws ForexException;
	
	/**
	 * 
	 * @param fileName
	 * @return {@link List of Deal}
	 */
	List<Deal> getDealsByFileName(String fileName);
}
