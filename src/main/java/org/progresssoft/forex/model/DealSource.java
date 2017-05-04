package org.progresssoft.forex.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents the source of deals. In Our case it would be file.
 * 
 * @author hafeeztsd
 *
 */
@Document(collection = "deal_source")
public class DealSource {

	@Id
	private String name;
	private Integer totalDealsCount;
	private Integer validDealsCount;
	private Integer invalidDealsCount;
	private String timestamp;

	public DealSource(){
		
	}
	
	public DealSource(String name, Integer totalDealsCount, Integer validDealsCount, Integer invalidDealsCount,
			String timestamp) {
		this.name = name;
		this.totalDealsCount = totalDealsCount;
		this.validDealsCount = validDealsCount;
		this.invalidDealsCount = invalidDealsCount;
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public Integer getTotalDealsCount() {
		return totalDealsCount;
	}

	public Integer getValidDealsCount() {
		return validDealsCount;
	}

	public Integer getInvalidDealsCount() {
		return invalidDealsCount;
	}

	public String getTimestamp() {
		return timestamp;
	}

}
