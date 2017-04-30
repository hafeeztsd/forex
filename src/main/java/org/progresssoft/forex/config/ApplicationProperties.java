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
	@Value("${data.file.directory.path}")
	private String dataFileDirectoryPath;
	@Value("${data.file.format}")
	private String fileFormat;
	
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

	public String getDataFileDirectoryPath() {
		return dataFileDirectoryPath;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	@Override
	public String toString() {
		return "ApplicationProperties [name=" + name + ", dbName=" + dbName + ", dbHost=" + dbHost + ", dbPort="
				+ dbPort + ", dataFileDirectoryPath=" + dataFileDirectoryPath + ", fileFormat=" + fileFormat + "]";
	}

}
