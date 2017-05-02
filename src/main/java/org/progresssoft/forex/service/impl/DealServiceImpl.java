package org.progresssoft.forex.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.progresssoft.forex.exception.ForexErrorCode;
import org.progresssoft.forex.exception.ForexException;
import org.progresssoft.forex.model.DealSource;
import org.progresssoft.forex.model.InvalidDeal;
import org.progresssoft.forex.model.ValidDeal;
import org.progresssoft.forex.repository.DealSourceRespository;
import org.progresssoft.forex.repository.InvalidDealRespository;
import org.progresssoft.forex.repository.ValidDealRespository;
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

	private static final String EMPTY_VALUE = "";

	private static final String SEPARATOR = ",";

	private static final int AMOUNT = 4;

	private static final int TIMESTAMP = 3;

	private static final int TO_CURRENCY = 2;

	private static final int FROM_CURRENCY = 1;

	private static final int DEAL_UNIQUE_ID = 0;

	private static final Logger LOGGER = Logger.getLogger(DealServiceImpl.class.getName());

	@Autowired
	ValidDealRespository validDealRespository;

	@Autowired
	InvalidDealRespository invalidDealRespository;

	@Autowired
	DealSourceRespository dealSourceRespository;

	@Override
	public void loadDeals(InputStream is, String source) throws ForexException {

		DealSource dealSource = dealSourceRespository.findOne(source);

		if (dealSource != null) {
			throw new ForexException(ForexErrorCode.ALREADY_UPLOADED);
		}

		Timer timer = new Timer();
		timer.start();
		List<InvalidDeal> invalidDeals = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

			List<ValidDeal> validDeals = br.lines().map(mapToDeal).filter(deal -> isValidDeal(deal, invalidDeals))
					.collect(Collectors.toList());

			LOGGER.info("Valid Deals to be loaded into DB are " + validDeals.size());
			LOGGER.info("Invalid Deals to be loaded into DB are " + invalidDeals.size());

			Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
			dealSource = new DealSource(source, (validDeals.size() + invalidDeals.size()), validDeals.size(),
					invalidDeals.size(), timestamp.toString());

			dealSourceRespository.save(dealSource);
			validDealRespository.save(validDeals);
			invalidDealRespository.save(invalidDeals);

			timer.stop();
			LOGGER.info("Time taken to load " + validDeals.size() + " is " + timer.getSconds() + " seconds.");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new ForexException(ForexErrorCode.SYSTEM_ERROR, e);
		}

	}

	private boolean isValidDeal(ValidDeal validDeal, List<InvalidDeal> invalidDeals) {
		if (!validDeal.isValidDeal()) {
			invalidDeals.add(new InvalidDeal(validDeal.getId(), validDeal.getFromCurrencyCode(),
					validDeal.getToCurrencyCode(), validDeal.getTimestamp(), validDeal.getAmount()));
			return false;
		}
		return true;
	}

	private static Function<String, ValidDeal> mapToDeal = (line) -> {
		String[] deal = line.split(SEPARATOR);
		return new ValidDeal(deal[DEAL_UNIQUE_ID] == null ? EMPTY_VALUE : deal[DEAL_UNIQUE_ID],
				deal[FROM_CURRENCY] == null ? EMPTY_VALUE : deal[FROM_CURRENCY],
				deal[TO_CURRENCY] == null ? EMPTY_VALUE : deal[TO_CURRENCY],
				deal[TIMESTAMP] == null ? EMPTY_VALUE : deal[TIMESTAMP],
				deal.length - 1 >= AMOUNT ? (deal[AMOUNT] == null ? 0 : Float.valueOf(deal[AMOUNT])) : 0);
	};

}
