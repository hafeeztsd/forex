package org.progresssoft.forex.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Forex Application.
 * 
 * @author hafeeztsd
 *
 */
@Configuration
public class ApplicationProperties {

	@Value("${spring.application.name}")
	private String name;
	@Value("${spring.data.mongodb.database}")
	private String dbName;
	@Value("${spring.data.mongodb.host}")
	private String dbHost;
	@Value("${spring.data.mongodb.port}")
	private Integer dbPort;
	
	public String getName() {
		return name;
	}

	public String getDbName() {
		return dbName;
	}

	public String getDbHost() {
		return dbHost;
	}

	public Integer getDbPort() {
		return dbPort;
	}

	@Override
	public String toString() {
		return "ApplicationProperties [name=" + name + ", dbName=" + dbName + ", dbHost=" + dbHost + ", dbPort="
				+ dbPort + "]";
	}

	

}
