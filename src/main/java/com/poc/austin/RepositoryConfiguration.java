package com.poc.austin;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * CRUD opertion 
 * @author Somnath
 *
 */

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = { "com.poc.austin.domain" })
@EnableJpaRepositories(basePackages = { "com.poc.austin.repositories" })
@EnableTransactionManagement

/*
 * Configuration JPA repository location so that Spring data can find our all
 * defined repository interfaces that are extending CrudRepository.
 * 
 * And load the Embedded Database for HSQL
 */
public class RepositoryConfiguration {

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
				.addScript("/data.sql").setName("PRODUCT").build();
	}
}