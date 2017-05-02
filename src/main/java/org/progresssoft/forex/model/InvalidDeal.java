package org.progresssoft.forex.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invalid_deals")
public class InvalidDeal extends Deal {

	public InvalidDeal() {
		super();
	}

	public InvalidDeal(String id, String fromCurrencyCode, String toCurrencyCode, String timestamp, float amount,
			String fileName) {
		super(id, fromCurrencyCode, toCurrencyCode, timestamp, amount, fileName);
	}

}
