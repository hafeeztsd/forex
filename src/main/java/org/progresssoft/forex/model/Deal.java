package org.progresssoft.forex.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Domain model for deal.
 * 
 * @author hafeeztsd
 *
 */

public class Deal {

	@Id
	/** Deal Unique Id **/
	private String id;

	/** Ordering Currency ISO Code **/
	private String fromCurrencyCode;

	/** To Currency ISO Code **/
	private String toCurrencyCode;

	/** Deal timestamp **/
	private String timestamp;

	/** Deal Amount in ordering currency **/
	private float amount;
	@Indexed
	/** File name or source from where this deals came from. **/
	private String fileName;

	public Deal() {
	}

	public Deal(String id, String fromCurrencyCode, String toCurrencyCode, String timestamp, float amount,
			String fileName) {
		this.id = id;
		this.fromCurrencyCode = fromCurrencyCode;
		this.toCurrencyCode = toCurrencyCode;
		this.timestamp = timestamp;
		this.amount = amount;
		this.fileName = fileName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromCurrencyCode() {
		return fromCurrencyCode;
	}

	public void setFromCurrencyCode(String fromCurrencyCode) {
		this.fromCurrencyCode = fromCurrencyCode;
	}

	public String getToCurrencyCode() {
		return toCurrencyCode;
	}

	public void setToCurrencyCode(String toCurrencyCode) {
		this.toCurrencyCode = toCurrencyCode;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "Deal [id=" + id + ", fromCurrencyCode=" + fromCurrencyCode + ", toCurrencyCode=" + toCurrencyCode
				+ ", timestamp=" + timestamp + ", amount=" + amount + ", fileName=" + fileName + "]";
	}

}
