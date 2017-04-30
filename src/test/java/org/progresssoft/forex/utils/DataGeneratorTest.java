package org.progresssoft.forex.utils;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.progresssoft.forex.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for {@link DataGenerator }
 * 
 * @author hafeeztsd
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
public class DataGeneratorTest {

	@Autowired
	DataGenerator dataGenerator;

	@Test
	public void shouldGenerateDataFile() {
		try {
			File file = dataGenerator.generateDataFile();
			Assert.assertNotNull("Generated file object is null", file);
		} catch (Exception e) {
			Logger.getLogger(DataGeneratorTest.class.getName()).log(Level.SEVERE, "Error while generatig data file", e);
			Assert.fail(e.getMessage());
		}
	}
}
