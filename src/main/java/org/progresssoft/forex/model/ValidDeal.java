package org.progresssoft.forex.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

@Document(collection = "valid_deals")
public class ValidDeal extends Deal {

	public ValidDeal() {

	}

	public ValidDeal(String id, String fromCurrencyCode, String toCurrencyCode, String timestamp, float amount,
			String fileName) {
		super(id, fromCurrencyCode, toCurrencyCode, timestamp, amount, fileName);
	}

	public boolean isValidDeal() {
		return !StringUtils.isEmpty(this.getId()) && !StringUtils.isEmpty(this.getFromCurrencyCode())
				&& !StringUtils.isEmpty(this.getToCurrencyCode()) && this.getTimestamp() != null
				&& this.getAmount() > 0;
	}

}
