package com.demo.spring.batch.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.demo.spring.batch.commons.FileConstants;

/**
 * @author Surya Tiwari
 */
@Configuration
@EnableBatchProcessing
@PropertySource(value = "classpath:batch.properties")
public class StandaloneInfrastructureConfiguration implements InfrastructureConfiguration {

	@Autowired
	protected Environment environment;

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
		return embeddedDatabaseBuilder
				.addScript("classpath:org/springframework/batch/core/schema-drop-hsqldb.sql")
				.addScript("classpath:org/springframework/batch/core/schema-hsqldb.sql")
				.setType(EmbeddedDatabaseType.HSQL)
				.build();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		/*
		 * ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		 * taskExecutor.setMaxPoolSize(4); taskExecutor.afterPropertiesSet();
		 */

		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(-1);
		return taskExecutor;
	}

	@PostConstruct
	public void loadProperties() {
		FileConstants.FILE_SOURCE_DIR = environment.getProperty("file.source.dir");
		FileConstants.FILE_DESTINATION_DIR = environment.getProperty("file.destination.dir");
	}

}
