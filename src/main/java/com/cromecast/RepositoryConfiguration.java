package com.cromecast;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.cromecast.domain"})
@EnableJpaRepositories(basePackages = {"com.cromecast.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
	
	
	@Bean(name = "dataSource")
	public DataSource  dataSource() {
		 return new EmbeddedDatabaseBuilder()
         .setType(EmbeddedDatabaseType.HSQL)         
         .addScript("/data.sql")
         .setName("STUDENT")
         .build();
	}
}