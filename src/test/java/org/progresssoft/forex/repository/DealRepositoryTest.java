package org.progresssoft.forex.repository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.progresssoft.forex.Application;
import org.progresssoft.forex.model.Deal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for {@link DealRespository}
 * 
 * @author hafeeztsd
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
public class DealRepositoryTest {

	private static final int MAX_DEAL_AMOUNT = 1000;

	private static final String CURRENCY_CODE_AED = "AED";

	private static final Logger LOGGER = Logger.getLogger(DealRepositoryTest.class.getName());

	@Autowired
	DealRespository dealRespository;

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Test
	public void shouldSaveDeal() {
		Deal deal = createDeal();
		deal = dealRespository.save(deal);
		LOGGER.info("Deal " + deal + " saved successfully.");
		Assert.assertNotNull("Deal is null ", deal);
	}

	@Test
	public void shouldFindDeal() {
		Deal deal = createDeal();
		deal = dealRespository.save(deal);
		LOGGER.info("Deal " + deal + " saved successfully.");
		deal = dealRespository.findOne(deal.getId());
		LOGGER.info("Found Deal " + deal + " for deal ID " + deal.getId());
		Assert.assertNotNull("Deal is null ", deal);
	}

	/**
	 * Create a new Test Deal.
	 * 
	 * @return {@link Deal}
	 */
	private Deal createDeal() {
		Deal deal = new Deal();
		deal.setAmount(new Random(MAX_DEAL_AMOUNT).nextFloat());
		deal.setFromCurrencyCode(CURRENCY_CODE_AED);
		deal.setId(String.valueOf(UUID.randomUUID()));
		Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
		deal.setTimestamp(timestamp.toString());
		deal.setToCurrencyCode(CURRENCY_CODE_AED);
		return deal;
	}

}