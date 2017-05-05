package org.progresssoft.forex.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.progresssoft.forex.exception.ForexErrorCode;
import org.progresssoft.forex.exception.ForexException;
import org.progresssoft.forex.model.CurrencyFrequency;
import org.progresssoft.forex.model.Deal;
import org.progresssoft.forex.model.DealSource;
import org.progresssoft.forex.model.InvalidDeal;
import org.progresssoft.forex.model.ValidDeal;
import org.progresssoft.forex.repository.CurrencyFrequencyRespository;
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

	private static final String SEPARATOR = ",";
	private static final int AMOUNT_INDEX = 4;
	private static final int TIMESTAMP_INDEX = 3;
	private static final int TO_CURRENCY_INDEX = 2;
	private static final int FROM_CURRENCY_INDEX = 1;
	private static final int DEAL_UNIQUE_ID_INDEX = 0;
	private static final Logger LOGGER = Logger.getLogger(DealServiceImpl.class.getName());

	@Autowired
	ValidDealRespository validDealRespository;
	@Autowired
	InvalidDealRespository invalidDealRespository;
	@Autowired
	DealSourceRespository dealSourceRespository;
	@Autowired
	CurrencyFrequencyRespository currencyFrequencyRespository;

	@Override
	public List<Deal> getDealsByFileName(String fileName) {
		List<Deal> deals = new ArrayList<>(validDealRespository.findByFileName(fileName));
		deals.addAll(invalidDealRespository.findByFileName(fileName));
		LOGGER.info("Total deals found for file name = " + fileName + " are = " + deals.size());
		return deals;
	}

	@Override
	public boolean loadDeals(InputStream is, final String source) throws ForexException {

		/**
		 * Making sure that file has not been processed earlier.
		 * 
		 */
		DealSource dealSource = dealSourceRespository.findOne(source);
		if (dealSource != null) {
			LOGGER.log(Level.SEVERE, ForexErrorCode.ALREADY_UPLOADED.getMessage());
			throw new ForexException(ForexErrorCode.ALREADY_UPLOADED);
		}

		/**
		 * Converting Each record of given CSV file into either ValidDeal or
		 * InvalidDeal. Saving both types of deals into their corresponding
		 * documents.
		 */
		Timer timer = new Timer();
		timer.start();
		List<InvalidDeal> invalidDeals = new ArrayList<>();
		Map<String, Integer> currencyFrequencyMap = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

			List<ValidDeal> validDeals = br.lines().map(line -> {

				String[] deal = line.split(SEPARATOR);
				return new ValidDeal(deal[DEAL_UNIQUE_ID_INDEX], deal[FROM_CURRENCY_INDEX], deal[TO_CURRENCY_INDEX],
						deal[TIMESTAMP_INDEX], deal.length - 1 >= AMOUNT_INDEX
								? (deal[AMOUNT_INDEX] == null ? 0 : Float.valueOf(deal[AMOUNT_INDEX])) : 0,
						source);

			}).filter(deal -> isValidDeal(deal, invalidDeals, currencyFrequencyMap, source))
					.collect(Collectors.toList());

			LOGGER.info("Valid Deals to be loaded into DB are " + validDeals.size());
			LOGGER.info("Invalid Deals to be loaded into DB are " + invalidDeals.size());

			Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
			dealSource = new DealSource(source, (validDeals.size() + invalidDeals.size()), validDeals.size(),
					invalidDeals.size(), timestamp.toString());

			/**
			 * Updating Deal Count for Ordering Currecny
			 */
			new Thread(new Runnable() {

				public void run() {
					Timer dealCountTimer = new Timer();
					dealCountTimer.start();

					List<CurrencyFrequency> storedFrequencies = currencyFrequencyRespository.findAll();

					for (CurrencyFrequency storedFrequency : storedFrequencies) {
						String key = storedFrequency.getCurrencyCode();
						if (currencyFrequencyMap.containsKey(key)) {
							currencyFrequencyMap.put(key,
									storedFrequency.getCountOfDeals() + currencyFrequencyMap.get(key));
						}
					}

					List<CurrencyFrequency> currencyFrequencies = currencyFrequencyMap.entrySet().stream()
							.map(e -> new CurrencyFrequency(e.getKey(), e.getValue())).collect(Collectors.toList());
					LOGGER.info("Preparing to add accumulative count for each ordering currecy. Total records are "
							+ currencyFrequencies.size());
					currencyFrequencyRespository.save(currencyFrequencies);
					dealCountTimer.stop();
					LOGGER.info("Time taken to add/ update the accumulative count for each currency is "
							+ timer.getMilliSeconds() + " ms, " + dealCountTimer.getSconds() + " seconds.");

				}
			}).start();
			
			
			dealSourceRespository.save(dealSource);
			validDealRespository.save(validDeals);
			invalidDealRespository.save(invalidDeals);
			timer.stop();
			LOGGER.info("Time taken to load " + validDeals.size() + " is " + timer.getSconds() + " seconds.");

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new ForexException(ForexErrorCode.SYSTEM_ERROR, e);
		}
		return true;
	}

	/**
	 * Validates the given {@link ValidDeal} and maintain ordering currency
	 * count if the provided {@link ValidDeal} is valid.
	 * 
	 * @param validDeal
	 * @param invalidDeals
	 * @param currencyFrequency
	 * @param fileName
	 * @return {@link Boolean}
	 */
	private boolean isValidDeal(ValidDeal validDeal, List<InvalidDeal> invalidDeals,
			Map<String, Integer> currencyFrequency, String fileName) {
		if (!validDeal.isValidDeal()) {
			invalidDeals.add(new InvalidDeal(validDeal.getId(), validDeal.getFromCurrencyCode(),
					validDeal.getToCurrencyCode(), validDeal.getTimestamp(), validDeal.getAmount(), fileName));
			return false;
		}
		currencyFrequency.compute(validDeal.getFromCurrencyCode(),
				(tokenKey, oldValue) -> oldValue == null ? 1 : oldValue + 1);

		return true;
	}

}
