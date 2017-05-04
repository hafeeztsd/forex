package org.progresssoft.forex.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.progresssoft.forex.Application;
import org.progresssoft.forex.model.Deal;
import org.progresssoft.forex.model.InvalidDeal;
import org.progresssoft.forex.model.ValidDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for {@link ValidDealRespository}
 * 
 * @author hafeeztsd
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
public class DealRepositoryTest {

	private static final String VALID_UNIT_TEST_CSV = "valid-unit-test.csv";
	private static final String INVALID_UNIT_TEST_CSV = "invalid-unit-test.csv";
	private static final int MAX_DEAL_AMOUNT = 1000;
	private static final String CURRENCY_CODE_AED = "AED";
	private static final Logger LOGGER = Logger.getLogger(DealRepositoryTest.class.getName());

	@Autowired
	ValidDealRespository dealRespository;
	@Autowired
	InvalidDealRespository invalidDealRespository;

	@Test
	public void shouldFindAllValidDealsByFileName() {
		int dealsCount = 5;
		dealRespository.save(createValidDeals(dealsCount));
		List<ValidDeal> validDeals = dealRespository.findByFileName(VALID_UNIT_TEST_CSV);
		LOGGER.info("Records found agains file " + VALID_UNIT_TEST_CSV + " are " + validDeals.size());
		Assert.assertTrue("Valid deals count mismatch ", validDeals.size() == dealsCount);
	}

	@Test
	public void shouldDeleteValidDealsByFileName() {
		int dealsCount = 5;
		dealRespository.save(createValidDeals(dealsCount));
		List<ValidDeal> validDeals = dealRespository.deleteByFileName(VALID_UNIT_TEST_CSV);
		LOGGER.info("Records deleted agains file " + VALID_UNIT_TEST_CSV + " are " + validDeals.size());
		Assert.assertTrue("Valid deals count mismatch ", validDeals.size() == dealsCount);
	}

	@Test
	public void shouldFindAllInValidDealsByFileName() {
		int dealsCount = 5;
		invalidDealRespository.save(createInValidDeals(dealsCount));
		List<InvalidDeal> invalidDeals = invalidDealRespository.findByFileName(INVALID_UNIT_TEST_CSV);
		LOGGER.info("Records found agains file " + INVALID_UNIT_TEST_CSV + " are " + invalidDeals.size());
		Assert.assertTrue("InValid deals count mismatch ", invalidDeals.size() == dealsCount);
	}

	@Test
	public void shouldDeleteInValidDealsByFileName() {
		int dealsCount = 5;
		invalidDealRespository.save(createInValidDeals(dealsCount));
		List<InvalidDeal> invalidDeals = invalidDealRespository.deleteByFileName(INVALID_UNIT_TEST_CSV);
		LOGGER.info("Records deleted agains file " + INVALID_UNIT_TEST_CSV + " are " + invalidDeals.size());
		Assert.assertTrue("Valid deals count mismatch ", invalidDeals.size() == dealsCount);
	}

	@After
	public void cleanUpValidDeals() {
		LOGGER.info("Cleaning Up valid deals added by tests.");
		dealRespository.deleteByFileName(VALID_UNIT_TEST_CSV);
		LOGGER.info("Cleaning Up invalid deals added by tests.");
		invalidDealRespository.deleteByFileName(INVALID_UNIT_TEST_CSV);

	}

	@Test
	public void shouldSaveValidDeal() {
		ValidDeal deal = createValidDeal();
		deal = dealRespository.save(deal);
		LOGGER.info("Deal " + deal + " saved successfully.");
		Assert.assertNotNull("Deal is null ", deal);

	}

	@Test
	public void shouldSaveInvalidDeal() {
		InvalidDeal deal = createInValidDeal();
		deal = invalidDealRespository.save(deal);
		LOGGER.info("Deal " + deal + " saved successfully.");
		Assert.assertNotNull("Deal is null ", deal);
	}

	@Test
	public void shouldFindValidDeal() {
		ValidDeal deal = createValidDeal();
		deal = dealRespository.save(deal);
		LOGGER.info("Deal " + deal + " saved successfully.");
		deal = dealRespository.findOne(deal.getId());
		LOGGER.info("Found Deal " + deal + " for deal ID " + deal.getId());
		Assert.assertNotNull("Deal is null ", deal);
	}

	@Test
	public void shouldFindInvalidDeal() {
		InvalidDeal deal = createInValidDeal();
		deal = invalidDealRespository.save(deal);
		LOGGER.info("Deal " + deal + " saved successfully.");
		deal = invalidDealRespository.findOne(deal.getId());
		LOGGER.info("Found Deal " + deal + " for deal ID " + deal.getId());
		Assert.assertNotNull("Deal is null ", deal);
	}

	/**
	 * Create a new Test Deal.
	 * 
	 * @return {@link Deal}
	 */
	private ValidDeal createValidDeal() {
		ValidDeal deal = new ValidDeal();
		deal.setAmount(new Random(MAX_DEAL_AMOUNT).nextFloat());
		deal.setFromCurrencyCode(CURRENCY_CODE_AED);
		deal.setId(String.valueOf(UUID.randomUUID()));
		Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
		deal.setTimestamp(timestamp.toString());
		deal.setToCurrencyCode(CURRENCY_CODE_AED);
		deal.setFileName(VALID_UNIT_TEST_CSV);
		return deal;
	}

	private List<ValidDeal> createValidDeals(int dealsCount) {
		List<ValidDeal> deals = new ArrayList<>();
		for (int i = 0; i < dealsCount; i++) {
			deals.add(createValidDeal());
		}
		return deals;
	}

	/**
	 * Create a new Test Deal.
	 * 
	 * @return {@link Deal}
	 */
	private InvalidDeal createInValidDeal() {
		InvalidDeal deal = new InvalidDeal();
		Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
		deal.setTimestamp(timestamp.toString());
		deal.setToCurrencyCode(CURRENCY_CODE_AED);
		deal.setFileName(INVALID_UNIT_TEST_CSV);
		return deal;
	}

	private List<InvalidDeal> createInValidDeals(int dealsCount) {
		List<InvalidDeal> deals = new ArrayList<>();
		for (int i = 0; i < dealsCount; i++) {
			deals.add(createInValidDeal());
		}
		return deals;
	}

}
