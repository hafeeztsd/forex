package org.progresssoft.forex.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * Spring Java Configuration for MongoDB.
 * 
 * @author hafeeztsd
 *
 */
@Configuration
@EnableMongoRepositories(basePackages = "org.progresssoft.forex.repository")
public class MongoDbConfig {

	@Autowired
	ApplicationProperties applicationProperties;

	@Bean
	public Mongo mongo() throws Exception {
		Mongo mongo = new MongoClient(applicationProperties.getDbHost(), applicationProperties.getDbPort());
		return mongo;
	}

	@Bean
	public MongoTemplate mongoTemplate(Mongo mongo) throws Exception {
		return new MongoTemplate(mongo, applicationProperties.getDbName());
	}

}