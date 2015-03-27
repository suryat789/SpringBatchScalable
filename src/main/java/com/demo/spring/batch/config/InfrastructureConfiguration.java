package com.demo.spring.batch.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;

/**
 * @author Surya Tiwari
 */
public interface InfrastructureConfiguration {

	@Bean
	public abstract DataSource dataSource();

	@Bean
	public abstract TaskExecutor taskExecutor();

}