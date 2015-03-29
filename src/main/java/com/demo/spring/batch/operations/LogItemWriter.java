package com.demo.spring.batch.operations;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

/**
 * {@link ItemWriter} which only logs data it receives.
 * 
 * @author Surya Tiwari
 */
public class LogItemWriter<T> implements ItemWriter<T> {

	private static final Log LOG = LogFactory.getLog(LogItemWriter.class);

	/**
	 * @see ItemWriter#write(java.util.List)
	 */
	public void write(List<? extends T> data) throws Exception {
		LOG.info(data);
	}

}
