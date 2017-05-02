package org.progresssoft.forex.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.progresssoft.forex.Application;
import org.progresssoft.forex.exception.ForexErrorCode;
import org.progresssoft.forex.exception.ForexException;
import org.progresssoft.forex.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for {@link DealService}
 * 
 * @author hafeeztsd
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
public class DealServiceTest {

	@Autowired
	DealService dealService;

	@Test
	public void shouldLoadFile() throws ForexException, IOException {
		String name = "all_valid_records.csv";
		File file = new File(ClassLoader.getSystemResource(name).getPath());
		dealService.loadDeals(new FileInputStream(file), name);
	}

	@Test
	public void shouldThrowErrorForAlreadyUploadedFile() {
		String name = "small_data.csv";
		File file = new File(ClassLoader.getSystemResource(name).getPath());
		try {
			dealService.loadDeals(new FileInputStream(file), name);
			dealService.loadDeals(new FileInputStream(file), name);
			Assert.fail("System should not process same file twice.");
		} catch (FileNotFoundException e) {
			Assert.fail(e.getMessage());
		} catch (ForexException e) {
			Assert.assertTrue("Error Code mismatch", ForexErrorCode.ALREADY_UPLOADED == e.getForexErrorCode());
		}
	}

}
