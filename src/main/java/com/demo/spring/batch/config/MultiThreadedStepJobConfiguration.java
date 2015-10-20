package com.demo.spring.batch.config;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.demo.spring.batch.beans.Person;
import com.demo.spring.batch.commons.FileConstants;
import com.demo.spring.batch.commons.FileOperationUtils;
import com.demo.spring.batch.listeners.ProtocolListener;
import com.demo.spring.batch.operations.EmployeeItemProcessor;
import com.demo.spring.batch.operations.EmployeeItemWriter;
import com.demo.spring.batch.operations.PersonFixedLengthTokenizer;

/**
 * @author Surya Tiwari
 */
@Configuration
public class MultiThreadedStepJobConfiguration {

	private static final Log LOG = LogFactory.getLog(MultiThreadedStepJobConfiguration.class);
	@Autowired
	private JobBuilderFactory jobBuilders;

	@Autowired
	private StepBuilderFactory stepBuilders;

	@Autowired
	private InfrastructureConfiguration infrastructureConfiguration;

	private FlatFileItemReader<Person> fileReader = new FlatFileItemReader<Person>();

	private String fileName;

	@Bean
	public Job multiThreadedStepJob() {

		return jobBuilders.get("multiThreadedStepJob").listener(protocolListener()).start(step()).build();
	}

	@Bean
	public Step step() {
		return stepBuilders.get("step").<Person, Person> chunk(10)
				.reader(reader()).processor(processor()).writer(writer())
				.listener(new StepExecutionListener() {

					public void beforeStep(StepExecution stepExecution){
						LOG.info("BeforeStep Status: " + stepExecution.getStatus());
						final File fileSource = new File(FileConstants.FILE_SOURCE_DIR);
						if (fileSource.exists()) {
							final File[] files = fileSource.listFiles(File::isFile);

							for (File file : files) {
								fileName = file.getName();
								break;
							}
						}

						if (fileName != null && fileName.length() > 0) {
							fileReader.setResource(new FileSystemResource(FileConstants.FILE_SOURCE_DIR + File.separator + fileName));
						} else {
							//stepExecution.setStatus(BatchStatus.FAILED);
							stepExecution.setTerminateOnly();
						}
					}

					public ExitStatus afterStep(StepExecution stepExecution) {
						LOG.info("AfterStep Status: " + stepExecution.getStatus());
						LOG.info("AfterStep FileName: " + fileName);

						File processedFile = new File(FileConstants.FILE_SOURCE_DIR + File.separator + fileName);
						if (processedFile.exists()) {
							LOG.info("AfterJob File Exists");
							try {
								FileOperationUtils.moveLocalFile(FileConstants.FILE_SOURCE_DIR, FileConstants.FILE_DESTINATION_DIR, fileName);
							} catch (IOException e) {
								LOG.error("Exception in moving processed file", e);
							}
						}

						return null;
					}
				})
				.taskExecutor(infrastructureConfiguration.taskExecutor()).throttleLimit(4).build();
	}

	@Bean
	public ItemReader<Person> reader() {

		// we use a default line mapper to assign the content of each line to the Person object
		fileReader.setLineMapper(new DefaultLineMapper<Person>() {
			{
				// we use a custom fixed line tokenizer
				setLineTokenizer(new PersonFixedLengthTokenizer());
				// as field mapper we use the name of the fields in the Person class
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
					{
						// we create an object Person
						setTargetType(Person.class);
					}
				});
			}
		});
		return fileReader;
	}

	@Bean
	public ItemProcessor<Person, Person> processor() {
		return new EmployeeItemProcessor<Person>();
	}

	@Bean
	public ItemWriter<Person> writer() {
		return new EmployeeItemWriter<Person>();
	}

	@Bean
	public ProtocolListener protocolListener() {
		return new ProtocolListener();
	}

}
