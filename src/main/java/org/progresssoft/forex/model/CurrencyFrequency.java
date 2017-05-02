package org.progresssoft.forex.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Store the information about number of deals made against ordering currency.
 * 
 * @author hafeeztsd
 *
 */
@Document(collection = "currency_frequency")
public class CurrencyFrequency {
	@Id
	private String currencyCode;
	private int countOfDeals;

	public CurrencyFrequency(String currencyCode, int countOfDeals) {
		this.currencyCode = currencyCode;
		this.countOfDeals = countOfDeals;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public int getCountOfDeals() {
		return countOfDeals;
	}

}
