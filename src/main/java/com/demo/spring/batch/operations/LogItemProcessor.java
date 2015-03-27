package com.demo.spring.batch.operations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Dummy {@link ItemProcessor} which only logs data it receives.
 * @author Surya Tiwari
 */
public class LogItemProcessor<T> implements ItemProcessor<T, T> {

	private static final Log LOG = LogFactory.getLog(LogItemProcessor.class);

	public T process(T item) throws Exception {
		LOG.info(item);
		return item;
	}

}
