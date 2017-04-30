package org.progresssoft.forex.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.progresssoft.forex.exception.ForexException;
import org.progresssoft.forex.model.Deal;
import org.progresssoft.forex.repository.DealRespository;
import org.progresssoft.forex.service.DealService;
import org.progresssoft.forex.utils.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link DealService}
 * 
 * @author hafeeztsd
 *
 */
@Service
public class DealServiceImpl implements DealService {

	private static final String SEPARATOR = ",";

	private static final int AMOUNT = 4;

	private static final int TIMESTAMP = 3;

	private static final int TO_CURRENCY = 2;

	private static final int FROM_CURRENCY = 1;

	private static final int DEAL_UNIQUE_ID = 0;

	private static final Logger LOGGER = Logger.getLogger(DealServiceImpl.class.getName());

	@Autowired
	DealRespository dealRespository;

	public Deal save(Deal deal) {
		return dealRespository.save(deal);
	}

	@Override
	public void loadDeals(InputStream is, String source) throws ForexException {
		Timer timer = new Timer();
		timer.start();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			List<Deal> deals = br.lines().map(mapToDeal).filter(deal -> deal.getId() != null)
					.collect(Collectors.toList());
			Logger.getLogger(DealServiceImpl.class.getName()).info("Deals to be loaded into DB are " + deals.size());
			dealRespository.save(deals);
			timer.stop();
			LOGGER.info("Time taken to load " + deals.size() + " is " + timer.getSconds() + " seconds.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ForexException("Error while loading file " + source + " to database.");
		}

	}

	private static Function<String, Deal> mapToDeal = (line) -> {
		String[] deal = line.split(SEPARATOR);

		return new Deal(deal[DEAL_UNIQUE_ID], deal[FROM_CURRENCY], deal[TO_CURRENCY],
				deal[TIMESTAMP], Float.valueOf(deal[AMOUNT]));
	};
	
	
	
}
