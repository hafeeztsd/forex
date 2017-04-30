package org.progresssoft.forex.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.progresssoft.forex.config.ApplicationProperties;
import org.progresssoft.forex.model.Deal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Utility class that is responsible for creating test forx data.
 * 
 * @author hafeeztsd
 *
 */
@Component
public class DataGenerator {

	private static final int MAX_RECORDS = 100000;
	private static final String FIELD_SEPARATOR = ",";
	private static final int TOTAL_CURRENCY_CODES = 180;

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	Environment environment;

	private List<String> currencyCodes;

	/**
	 * Generate data file as per application configuration.
	 * 
	 * @return {@link File}
	 */
	public File generateDataFile() throws Exception {
		String fileName = new StringBuilder(applicationProperties.getDataFileDirectoryPath()).append("/")
				.append(System.nanoTime()).append(".").append(applicationProperties.getFileFormat()).toString();
		File file = new File(fileName);
		String maxRecords = environment.getProperty("max.record.count");
		Integer recordsInDataFile = maxRecords == null ? MAX_RECORDS : Integer.valueOf(maxRecords);
		Logger.getLogger(this.getClass().getName()).info(" Creating Data file with records = " + recordsInDataFile);
		FileWriter writer = new FileWriter(file);
		for (int i = 0; i < recordsInDataFile; i++) {
			writer.append(createDealAsString()).append("\n");
		}
		writer.close();
		return file;
	}

	@PostConstruct
	public void loadCurrencyCodes() throws URISyntaxException, IOException {
		Stream<String> stream = null;
		try {
			currencyCodes = new ArrayList<String>();
			stream = Files.lines(Paths.get(ClassLoader.getSystemResource("currency-codes.csv").toURI()));
			currencyCodes = stream.collect(Collectors.toList());
			Logger.getLogger(DataGenerator.class.getName())
					.info("Total number of currencies loaded are " + currencyCodes.size());

		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * Create a deal in sring format where each field of {@link Deal} is
	 * separated by ","
	 * 
	 * @return {@link Deal}
	 */
	private String createDealAsString() {
		Random random = new Random();
		StringBuilder deal = new StringBuilder();
		deal.append(String.valueOf(UUID.randomUUID())).append(FIELD_SEPARATOR);
		deal.append(String.valueOf(currencyCodes.get(random.nextInt(TOTAL_CURRENCY_CODES)))).append(FIELD_SEPARATOR);
		deal.append(String.valueOf(currencyCodes.get(random.nextInt(TOTAL_CURRENCY_CODES)))).append(FIELD_SEPARATOR);
		deal.append(new Timestamp(Calendar.getInstance().getTime().getTime())).append(FIELD_SEPARATOR);
		deal.append(String.valueOf(new Random().nextFloat() * 10));
		return deal.toString();
	}

}
